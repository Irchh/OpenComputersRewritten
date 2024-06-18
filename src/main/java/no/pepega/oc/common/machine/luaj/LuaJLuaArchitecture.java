package no.pepega.oc.common.machine.luaj;

import com.google.common.base.Strings;
import li.cil.repack.org.luaj.vm2.*;
import li.cil.repack.org.luaj.vm2.lib.jse.JsePlatform;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import no.pepega.oc.OCSettings;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.api.Driver;
import no.pepega.oc.api.driver.item.Memory;
import no.pepega.oc.api.machine.Architecture;
import no.pepega.oc.api.machine.ExecutionResult;
import no.pepega.oc.api.machine.LimitReachedException;
import no.pepega.oc.api.machine.Machine;
import no.pepega.oc.api.machine.Signal;
import no.pepega.oc.util.LuaJHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Architecture.Name("LuaJ")
public class LuaJLuaArchitecture implements Architecture {
    private final Machine machine;
    private Globals lua;
    private LuaThread thread;
    private LuaFunction synchronizedCall;
    private LuaValue synchronizedResult;
    private boolean doneWithInitRun = false;

    private int memory = 0;

    public LuaJLuaArchitecture(Machine machine) {
        this.machine = machine;
    }

    public interface Supplier<T> {
        T get() throws Throwable;
    }

    private Varargs invoke(Supplier<List<Object>> f) {
        try {
            var results = f.get();
            List<LuaValue> a = results.stream().map(LuaJHelper::toLuaValue).collect(Collectors.toList());
            a.addFirst(LuaValue.TRUE);
            return LuaValue.varargsOf(a.toArray(LuaValue[]::new));
        } catch (Throwable t) {
            return switch (t) {
                case LimitReachedException e -> LuaValue.NONE;
                case IllegalArgumentException e -> LuaValue.varargsOf(LuaValue.FALSE, LuaValue.valueOf(e.getMessage() != null ? e.getMessage() : "bad argument"));
                case IndexOutOfBoundsException e -> LuaValue.varargsOf(LuaValue.FALSE, LuaValue.valueOf("index out of bounds"));
                case NoSuchMethodException e -> LuaValue.varargsOf(LuaValue.FALSE, LuaValue.valueOf("no such method"));
                case FileNotFoundException e -> LuaValue.varargsOf(LuaValue.TRUE, LuaValue.NIL, LuaValue.valueOf("file not found"));
                case SecurityException e -> LuaValue.varargsOf(LuaValue.TRUE, LuaValue.NIL, LuaValue.valueOf("access denied"));
                case IOException e -> LuaValue.varargsOf(LuaValue.TRUE, LuaValue.NIL, LuaValue.valueOf("i/o error"));
                default -> LuaValue.varargsOf(LuaValue.TRUE, LuaValue.NIL, LuaValue.valueOf(t.getMessage() != null ? t.getMessage() : "unknown error"));
            };
        }
    }

    private Varargs documentation(Supplier<String> f) {
        try {
            var doc = f.get();
            if (Strings.isNullOrEmpty(doc)) return LuaValue.NIL;
            else return LuaValue.valueOf(doc);
        } catch (Throwable t) {
            return switch (t) {
                case NoSuchMethodException e -> LuaValue.varargsOf(LuaValue.NIL, LuaValue.valueOf("no such method"));
                default -> LuaValue.varargsOf(LuaValue.NIL, LuaValue.valueOf(t.getMessage() != null ? t.getMessage() : t.toString()));
            };
        }
    }

    // ----------------------------------------------------------------------- //

    @Override
    public boolean isInitialized() {
        return doneWithInitRun;
    }

    @Override
    public boolean recomputeMemory(Iterable<ItemStack> components) {
        memory = memoryInBytes(components);
        return memory > 0;
    }

    private int memoryInBytes(Iterable<ItemStack> components) {
        double totalMemory = 0.0;
        for (ItemStack stack : components) {
            Object driver = Driver.driverFor(stack);
            if (driver instanceof Memory) {
                Memory memoryDriver = (Memory) driver;
                totalMemory += memoryDriver.amount(stack) * 1024;
            }
        }
        int result = (int) Math.max(0, Math.min(totalMemory, OCSettings.maxTotalRam));
        return result;
    }

    @Override
    public void runSynchronized() {
        synchronizedResult = synchronizedCall.call();
        synchronizedCall = null;
    }

    @Override
    public ExecutionResult runThreaded(boolean isSynchronizedReturn) {
        try {
            // Resume the Lua state and remember the number of results we get.
            Varargs results;
            if (isSynchronizedReturn) {
                // If we were doing a synchronized call, continue where we left off.
                results = thread.resume(synchronizedResult);
                synchronizedResult = null;
            } else {
                if (!doneWithInitRun) {
                    // We're doing the initialization run.
                    var result = thread.resume(LuaValue.NONE);
                    // Mark as done *after* we ran, to avoid switching to synchronized
                    // calls when we actually need direct ones in the init phase.
                    doneWithInitRun = true;
                    // We expect to get nothing here, if we do we had an error.
                    if (result.narg() != 1) {
                        results = result;
                    } else {
                        // Fake zero sleep to avoid stopping if there are no signals.
                        results = LuaValue.varargsOf(LuaValue.TRUE, LuaValue.valueOf(0));
                    }
                } else {
                    var signal = machine.popSignal();
                    if (signal != null) {
                        results = thread.resume(LuaValue.varargsOf(Stream.concat(
                                Stream.of(LuaValue.valueOf(signal.name())),
                                Arrays.stream(signal.args()).map(LuaJHelper::toLuaValue)
                        ).toArray(LuaValue[]::new)));
                    } else {
                        results = thread.resume(LuaValue.NONE);
                    }
                }
            }

            // Check if the kernel is still alive.
            if (thread.state.status == LuaThread.STATUS_SUSPENDED) {
                // If we get one function it must be a wrapper for a synchronized
                // call. The protocol is that a closure is pushed that is then called
                // from the main server thread, and returns a table, which is in turn
                // passed to the originating coroutine.yield().
                if (results.narg() == 2 && results.isfunction(2)) {
                    synchronizedCall = results.checkfunction(2);
                    return new ExecutionResult.SynchronizedCall();
                }
                // Check if we are shutting down, and if so if we're rebooting. This
                // is signalled by boolean values, where `false` means shut down,
                // `true` means reboot (i.e shutdown then start again).
                else if (results.narg() == 2 && results.type(2) == LuaValue.TBOOLEAN) {
                    return new ExecutionResult.Shutdown(results.toboolean(2));
                }
                else {
                    // If we have a single number, that's how long we may wait before
                    // resuming the state again. Note that the sleep may be interrupted
                    // early if a signal arrives in the meantime. If we have something
                    // else we just process the next signal or wait for one.
                    var ticks = (results.narg() == 2 && results.isnumber(2)) ? (int)(results.todouble(2) * 20) : Integer.MAX_VALUE;
                    return new ExecutionResult.Sleep(ticks);
                }
            }
            // The kernel thread returned. If it threw we'd be in the catch below.
            else {
                // This is a little... messy because we run a pcall inside the kernel
                // to be able to catch errors before JNLua gets its claws on them. So
                // we can either have (boolean, string | error) if the main kernel
                // fails, or (boolean, boolean, string | error) if something inside
                // that pcall goes bad.
                var isInnerError = results.type(2) == LuaValue.TBOOLEAN && (results.isstring(3) || results.isnoneornil(3));
                var isOuterError = results.isstring(2) || results.isnoneornil(2);
                if (results.type(1) != LuaValue.TBOOLEAN || !isInnerError || !isOuterError) {
                    OpenComputersRewritten.log.warn("Kernel returned unexpected results.");
                }
                // The pcall *should* never return normally... but check for it nonetheless.
                if ((isOuterError && results.toboolean(1)) || (isInnerError && results.toboolean(2))) {
                    OpenComputersRewritten.log.warn("Kernel stopped unexpectedly.");
                    return new ExecutionResult.Shutdown(false);
                } else {
                    String error;
                    if (isInnerError) {
                        if (results.isuserdata(3))
                            error = results.touserdata(3).toString();
                        else
                            error = results.tojstring(3);
                    } else if (results.isuserdata(2))
                        error = results.touserdata(2).toString();
                    else
                        error = results.tojstring(2);
                    if (error != null)
                        return new ExecutionResult.Error(error);
                    else
                        return new ExecutionResult.Error("unknown error");
                }
            }
        } catch (LuaError e) {
            OpenComputersRewritten.log.warn("Kernel crashed. This is a bug!", e);
            return new ExecutionResult.Error("kernel panic: this is a bug, check your log file and report it");
        } catch (Throwable e) {
            OpenComputersRewritten.log.warn("Unexpected error in kernel. This is a bug!", e);
            return new ExecutionResult.Error("kernel panic: this is a bug, check your log file and report it");
        }
    }

    @Override
    public void onSignal() {}

    // ----------------------------------------------------------------------- //

    @Override
    public boolean initialize() {
        lua = JsePlatform.debugGlobals();
        lua.set("package", LuaValue.NIL);
        lua.set("require", LuaValue.NIL);
        lua.set("io", LuaValue.NIL);
        lua.set("os", LuaValue.NIL);
        lua.set("luajava", LuaValue.NIL);

        // Remove some other functions we don't need and are dangerous.
        lua.set("dofile", LuaValue.NIL);
        lua.set("loadfile", LuaValue.NIL);

        //apis.foreach(_.initialize())

        recomputeMemory(machine.host().internalComponents());

        var kernel = lua.load(Machine.class.getResourceAsStream(OCSettings.scriptPath + "machine.lua"), "=machine", "t", lua);
        thread = new LuaThread(lua, kernel); // Left as the first value on the stack.

        return true;
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void close() {
        lua = null;
        thread = null;
        synchronizedCall = null;
        synchronizedResult = null;
        doneWithInitRun = false;
    }

    // ----------------------------------------------------------------------- //

    @Override
    public void loadData(NbtCompound nbt) {
        if (machine.isRunning()) {
            machine.stop();
            machine.start();
        }
    }

    @Override
    public void saveData(NbtCompound nbt) {

    }
}
