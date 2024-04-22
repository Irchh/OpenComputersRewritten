package no.pepega.oc.common.init;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.common.ui.CaseScreenHandler;

public class GUIs {
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER = new ScreenHandlerType<>(CaseScreenHandler::new, FeatureSet.empty());

    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "case"), CASE_SCREEN_HANDLER);
    }
}
