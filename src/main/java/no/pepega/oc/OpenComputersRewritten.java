package no.pepega.oc;

import net.fabricmc.api.ModInitializer;
import no.pepega.oc.common.init.*;
import no.pepega.oc.common.networking.Networking;

public class OpenComputersRewritten implements ModInitializer {
    public static String identifier = "opencomputers";

    @Override
    public void onInitialize() {
        System.out.println("OpenComputersRewritten init!");
        Networking.init();
        Sounds.init();
        Items.init();
        Blocks.init();
        BlockEntities.init();
        GUIs.init();
    }
}
