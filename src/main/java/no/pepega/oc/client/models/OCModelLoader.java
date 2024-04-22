package no.pepega.oc.client.models;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import no.pepega.oc.OpenComputersRewritten;

public class OCModelLoader implements ModelLoadingPlugin {
    public static final ModelIdentifier SCREEN_MODEL = new ModelIdentifier(OpenComputersRewritten.identifier, "screen1", "");

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        // We want to add our model when the models are loaded
        pluginContext.modifyModelOnLoad().register((original, context) -> {
            // This is called for every model that is loaded, so make sure we only target ours
            if(context.id().toString().startsWith("opencomputers:screen")) {
                System.out.println("Replacing model of " + context.id());
                if (context.id().toString().contains("inventory")) {
                    return new ScreenModel();
                } else {
                    return new ScreenModel();
                }
            } else {
                // If we don't modify the model we just return the original as-is
                return original;
            }
        });
    }
}
