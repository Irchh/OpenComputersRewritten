package no.pepega.oc.client.ui;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import no.pepega.oc.common.init.GUIs;

public class ClientHandlesScreens {
    public static void init() {
        HandledScreens.register(GUIs.CASE_SCREEN_HANDLER_TIER1, CaseHandledScreen::new);
        HandledScreens.register(GUIs.CASE_SCREEN_HANDLER_TIER2, CaseHandledScreen::new);
        HandledScreens.register(GUIs.CASE_SCREEN_HANDLER_TIER3, CaseHandledScreen::new);
        HandledScreens.register(GUIs.CASE_SCREEN_HANDLER_TIERC, CaseHandledScreen::new);
    }
}
