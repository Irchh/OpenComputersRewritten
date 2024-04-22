package no.pepega.oc;

import net.fabricmc.api.ModInitializer;
import no.pepega.oc.common.init.BlockEntities;
import no.pepega.oc.common.init.Blocks;
import no.pepega.oc.common.init.Items;

public class OpenComputersRewritten implements ModInitializer {
    public static String identifier = "opencomputers";

    @Override
    public void onInitialize() {
        System.out.println("OpenComputersRewritten init!");
        Items.init();
        Blocks.init();
        BlockEntities.init();
    }
}
