package no.pepega.oc.client.ui;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import no.pepega.oc.common.init.GUIs;

public class ClientHandlesScreens {
    public static void init() {
        HandledScreens.register(GUIs.CASE_SCREEN_HANDLER, CaseHandledScreen::new);
    }
}
