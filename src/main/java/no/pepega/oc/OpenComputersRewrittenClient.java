package no.pepega.oc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import no.pepega.oc.client.KeyBindings;
import no.pepega.oc.client.OCItemGroup;
import no.pepega.oc.client.Textures;
import no.pepega.oc.client.models.OCModelLoader;
import no.pepega.oc.client.ui.ClientHandledScreens;

import static no.pepega.oc.common.init.Blocks.runOnAllBlocks;

public class OpenComputersRewrittenClient implements ClientModInitializer {
    public Textures textures = null;

    @Override
    public void onInitializeClient() {
        OpenComputersRewritten.log.info("OpenComputersRewritten client init!");
        runOnAllBlocks(OCModelLoader::setColorOfCase);
        textures = new Textures();
        ModelLoadingPlugin.register(new OCModelLoader());
        ClientHandledScreens.init();
        KeyBindings.init();
        OCItemGroup.init();
    }
}
