package no.pepega.oc.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.common.ui.CaseScreenHandler;

public class GUIs {
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER_TIER1 = new ScreenHandlerType<>(CaseScreenHandler::tier1, FeatureSet.empty());
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER_TIER2 = new ScreenHandlerType<>(CaseScreenHandler::tier2, FeatureSet.empty());
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER_TIER3 = new ScreenHandlerType<>(CaseScreenHandler::tier3, FeatureSet.empty());
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER_TIERC = new ScreenHandlerType<>(CaseScreenHandler::creative, FeatureSet.empty());

    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "case1"), CASE_SCREEN_HANDLER_TIER1);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "case2"), CASE_SCREEN_HANDLER_TIER2);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "case3"), CASE_SCREEN_HANDLER_TIER3);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "casecreative"), CASE_SCREEN_HANDLER_TIERC);
    }
}
