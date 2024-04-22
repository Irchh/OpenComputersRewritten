package no.pepega.oc.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.common.ui.CaseScreenHandler;

public class CaseHandledScreen extends HandledScreen<CaseScreenHandler> {
    public CaseHandledScreen(CaseScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    Identifier slot = new Identifier(OpenComputersRewritten.identifier, "textures/gui/slot.png");
    private int slotSize = 18;

    Identifier background = new Identifier(OpenComputersRewritten.identifier, "textures/gui/background.png");
    private int backgroundWidth = 176;
    private int backgroundHeight = 166;

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawAt(context, x, y, backgroundWidth, 256, background);
        //in 1.20 or above,this method is in DrawContext class.
    }

    private void drawAt(DrawContext context, int x, int y, int w, int h, Identifier ident) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ident);
        context.drawTexture(ident, x, y, 0, 0, w, h);
    }

    private void renderPretty(DrawContext context) {
        drawAt(context, 142, 16 + slotSize, slotSize, slotSize, slot);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        renderPretty(context);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
