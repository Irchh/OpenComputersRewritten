package no.pepega.oc.api;

import no.pepega.oc.api.detail.DriverAPI;
import no.pepega.oc.api.detail.ItemAPI;
import no.pepega.oc.api.detail.MachineAPI;
import no.pepega.oc.api.detail.NetworkAPI;
import no.pepega.oc.common.driver.DriverAPIImplementation;
import no.pepega.oc.common.driver.ItemAPIImplementation;
import no.pepega.oc.common.machine.luaj.LuaJLuaArchitecture;

/**
 * Central reference for the API.
 * <br>
 * Don't use this class directly, prefer using the other classes in this
 * package instead. This class is initialized by OpenComputers in the
 * pre-init phase, so it should not be used before the init phase.
 */
public class API {
    // TODO: Should this be changed/removed? I'm assuming it's for compatibility checking for other mods(?)
    // TODO: If it is then it should probably be updated lol
    public static final String ID_OWNER = "opencomputers|core";
    public static final String VERSION = "7.0.0-alpha";

    // ----------------------------------------------------------------------- //

    /**
     * The loaded config.
     */
    //public static Config config = null;

    /**
     * Whether OpenComputers uses power.
     * <br>
     * This is set in the init phase, so do not rely it before the post-init phase.
     */
    public static boolean isPowerEnabled = false;

    // ----------------------------------------------------------------------- //
    // Prefer using the static methods in the respective classes in this package
    // over accessing these instances directly.

    public static DriverAPIImplementation driver = null;
    //public static FileSystemAPI fileSystem = null;
    public static ItemAPIImplementation items = null;
    public static MachineAPI machine = null;
    //public static ManualAPI manual = null;
    //public static NanomachinesAPI nanomachines = null;
    public static NetworkAPI network = null;

    // ----------------------------------------------------------------------- //

    public static void init() {
        driver = new DriverAPIImplementation();
        items = new ItemAPIImplementation();
        no.pepega.oc.api.Machine.add(LuaJLuaArchitecture.class);
    }

    private API() {
    }
}
