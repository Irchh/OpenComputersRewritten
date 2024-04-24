package no.pepega.oc.client.models;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class OCModelLoader implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        // We want to add our model when the models are loaded
        pluginContext.modifyModelOnLoad().register((original, context) -> {
            // This is called for every model that is loaded, so make sure we only target ours
            if(context.id().toString().startsWith("opencomputers:screen")) {
                return new ScreenModel();
            } else {
                // If we don't modify the model we just return the original as-is
                return original;
            }
        });
    }
}
