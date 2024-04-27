package no.pepega.oc.integration.opencomputers;

import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import no.pepega.oc.OCSettings;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.api.Items;
import no.pepega.oc.api.Machine;
import no.pepega.oc.api.driver.item.CallBudget;
import no.pepega.oc.api.driver.item.MutableProcessor;
import no.pepega.oc.api.driver.item.SlotType;
import no.pepega.oc.api.machine.Architecture;
import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.api.network.ManagedEnvironment;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.component.CPU;
import no.pepega.oc.common.init.OCDataComponentTypes;
import no.pepega.oc.common.item.util.CPULike;

import java.util.Collection;
import java.util.List;

public class DriverCPU extends Item implements MutableProcessor, CallBudget {
    @Override
    public boolean worksWith(ItemStack stack) {
        return isOneOf(stack,
                Items.get(no.pepega.oc.common.init.Items.cpu0.registryName()),
                Items.get(no.pepega.oc.common.init.Items.cpu1.registryName()),
                Items.get(no.pepega.oc.common.init.Items.cpu2.registryName())
            );
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new CPU(tier(stack));
    }

    @Override
    public SlotType slot(ItemStack stack) {
        return SlotType.CPU;
    }

    @Override
    public int tier(ItemStack stack) {
        return cpuTier(stack);
    }

    private int cpuTier(ItemStack stack) {
        if (stack.getItem() instanceof CPULike cpu) {
            return cpu.cpuTier();
        }
        return Tier.One;
    }

    @Override
    public int supportedComponents(ItemStack stack) {
        return OCSettings.cpuComponentSupport[cpuTier(stack)];
    }

    @Override
    public Collection<Class<? extends Architecture>> allArchitectures() {
        return Machine.architectures();
    }

    @Override
    public Class<? extends Architecture> architecture(ItemStack stack) {
        var nbtData = stack.get(OCDataComponentTypes.LEGACY_NBT_COMPAT);
        if (nbtData != null) {
            var nbt = nbtData.copyNbt();
            var archClass = nbt.getString(OCSettings.namespace + "archClass");
            // TODO: Implement NativeLuaArchitecture
            /*if (archClass == NativeLuaArchitecture.getName()) {
                // Migrate old saved CPUs to new versions (since the class they refer still
                // exists, but is abstract, which would lead to issues).
                archClass = Machine.LuaArchitecture.getName();
            }*/
            if (!archClass.isEmpty()) try {
                return Class.forName(archClass).asSubclass(Architecture.class);
            } catch (Throwable t) {
                OpenComputersRewritten.log.warn("Failed getting class for CPU architecture. Resetting CPU to use the default.", t);
                nbt.remove(OCSettings.namespace + "archClass");
                nbt.remove(OCSettings.namespace + "archName");
                stack.set(OCDataComponentTypes.LEGACY_NBT_COMPAT, NbtComponent.of(nbt));
            }
        }
        try {
            return Machine.architectures().iterator().next();
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    public void setArchitecture(ItemStack stack, Class<? extends Architecture> architecture) {
        if (!worksWith(stack)) throw new IllegalArgumentException("Unsupported processor type.");
        var data = stack.getOrDefault(OCDataComponentTypes.LEGACY_NBT_COMPAT, NbtComponent.DEFAULT).copyNbt();
        data.putString(OCSettings.namespace + "archClass", architecture.getName());
        data.putString(OCSettings.namespace + "archName", Machine.getArchitectureName(architecture));
        stack.set(OCDataComponentTypes.LEGACY_NBT_COMPAT, NbtComponent.of(data));
    }

    @Override
    public double getCallBudget(ItemStack stack) {
        return OCSettings.callBudgets[Math.min(Math.max(tier(stack), Tier.One), Tier.Three)];
    }
}
