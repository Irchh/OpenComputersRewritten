package no.pepega.oc;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import no.pepega.oc.api.API;
import no.pepega.oc.api.Driver;
import no.pepega.oc.common.init.*;
import no.pepega.oc.common.machine.luaj.LuaJLuaArchitecture;
import no.pepega.oc.common.networking.Networking;
import no.pepega.oc.common.registry.OCRegistry;
import no.pepega.oc.integration.opencomputers.DriverCPU;
import no.pepega.oc.integration.opencomputers.DriverEEPROM;
import no.pepega.oc.integration.opencomputers.DriverMemory;
import org.slf4j.Logger;

public class OpenComputersRewritten implements ModInitializer {
    public static String identifier = "opencomputers";

    public static final Logger log = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        OpenComputersRewritten.log.info("OpenComputersRewritten init!");
        Networking.init();
        OCDataComponentTypes.init();
        API.init();

        initDrivers();

        OCRegistry.register(LuaJLuaArchitecture.class);
        Sounds.init();
        Items.init();
        Blocks.init();
        BlockEntities.init();
        GUIs.init();
    }

    void initDrivers() {
        Driver.add(new DriverCPU());
        Driver.add(new DriverMemory());
        Driver.add(new DriverEEPROM());
        Driver.add(new DriverEEPROM.Provider());
    }
}
