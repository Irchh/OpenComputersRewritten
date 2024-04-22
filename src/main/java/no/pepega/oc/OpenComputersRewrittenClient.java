package no.pepega.oc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import no.pepega.oc.client.KeyBindings;
import no.pepega.oc.client.OCItemGroup;
import no.pepega.oc.client.Textures;
import no.pepega.oc.client.models.OCModelLoader;

public class OpenComputersRewrittenClient implements ClientModInitializer {
    public Textures textures = null;

    @Override
    public void onInitializeClient() {
        System.out.println("OpenComputersRewritten client init!");
        textures = new Textures();
        ModelLoadingPlugin.register(new OCModelLoader());
        
        KeyBindings.init();
        OCItemGroup.init();
    }
}
