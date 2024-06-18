package no.pepega.oc.client.models;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import no.pepega.oc.common.block.Case;
import no.pepega.oc.util.Color;

public class OCModelLoader implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        // We want to add our model when the models are loaded
        pluginContext.modifyModelOnLoad().register((original, context) -> {
            // This is called for every model that is loaded, so make sure we only target ours
            var dependencies = original.getModelDependencies();
            for (var dependency : dependencies) {
                // TODO: idk if this is correct, but it seems to work
                if (dependency.toString().equals("opencomputers:block/screen")) {
                    return new ScreenModel();
                }
            }
            // If we don't modify the model we just return the original as-is
            return original;
        });
    }

    public static void setColorOfCase(Block block) {
        if (block instanceof Case computerCase) {
            ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
                if (view == null || view.getBlockEntityRenderData(pos) == null) return Color.rgbValues.get(computerCase.blockTint());
                return (Integer)view.getBlockEntityRenderData(pos);
            }, computerCase);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Color.rgbValues.get(computerCase.blockTint()), computerCase);
        }
    }
}
