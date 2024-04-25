package no.pepega.oc.common.init;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.common.ui.CaseScreenHandler;

public class GUIs {
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER_TIER1 = new ExtendedScreenHandlerType<>(CaseScreenHandler::tier1, BlockPos.PACKET_CODEC.cast());
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER_TIER2 = new ExtendedScreenHandlerType<>(CaseScreenHandler::tier2, BlockPos.PACKET_CODEC.cast());
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER_TIER3 = new ExtendedScreenHandlerType<>(CaseScreenHandler::tier3, BlockPos.PACKET_CODEC.cast());
    public static ScreenHandlerType<CaseScreenHandler> CASE_SCREEN_HANDLER_TIERC = new ExtendedScreenHandlerType<>(CaseScreenHandler::creative, BlockPos.PACKET_CODEC.cast());

    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "case1"), CASE_SCREEN_HANDLER_TIER1);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "case2"), CASE_SCREEN_HANDLER_TIER2);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "case3"), CASE_SCREEN_HANDLER_TIER3);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(OpenComputersRewritten.identifier, "casecreative"), CASE_SCREEN_HANDLER_TIERC);
    }
}
