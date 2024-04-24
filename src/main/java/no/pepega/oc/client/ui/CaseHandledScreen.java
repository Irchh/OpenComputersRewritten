package no.pepega.oc.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.api.component.ComponentType;
import no.pepega.oc.common.block.inventory.ComponentSlot;
import no.pepega.oc.common.ui.CaseScreenHandler;

import java.util.Arrays;

public class CaseHandledScreen extends HandledScreen<CaseScreenHandler> {
    public CaseHandledScreen(CaseScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.literal("Computer"));
    }

    Identifier background = new Identifier(OpenComputersRewritten.identifier, "textures/gui/background.png");
    private int backgroundWidth = 176;
    private int backgroundHeight = 166;
    private int backgroundX;
    private int backgroundY;

    public static final int slotSize = 18;

    Identifier computerPCB = new Identifier(OpenComputersRewritten.identifier, "textures/gui/computer.png");

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        drawAt(context, backgroundX, backgroundY, backgroundWidth, backgroundHeight, 256, 256, background);
        drawAt(context, backgroundX, backgroundY, backgroundWidth, backgroundHeight, 256, 256, computerPCB);
        //in 1.20 or above,this method is in DrawContext class.
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.playerInventoryTitleX, this.titleY, 0x404040, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 0x404040, false);
    }

    private void drawAt(DrawContext context, int x, int y, int u, int v, int w, int h, int textureWidth, int textureHeight, Identifier ident) {
        // getPositionTexProgram
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ident);
        context.drawTexture(ident, x, y, 0, u, v, w, h, textureWidth, textureHeight);
    }

    private void drawAt(DrawContext context, int x, int y, int w, int h, int textureWidth, int textureHeight, Identifier ident) {
        drawAt(context, x, y, 0, 0, w, h, textureWidth, textureHeight, ident);
    }

    private void drawSlot(DrawContext context, ComponentSlot slot) {
        drawAt(context, slot.x-1, slot.y-1, 7, 83, slotSize, slotSize, 256, 256, background);
        ComponentType icon = slot.slotType();
        if (icon != ComponentType.None && icon != ComponentType.Filtered) {
            //Identifier tex = new Identifier(OpenComputersRewritten.identifier, "textures/icons/" + icon + ".png");
            //System.out.println("drawing slot type " + tex);
            Identifier iconIdent = new Identifier(OpenComputersRewritten.identifier, "textures/icons/" + icon.label + ".png");

            drawAt(context, slot.x, slot.y, 16, 16, 16, 16, iconIdent);
            if (Arrays.asList(0, 1, 2).contains(slot.tier())) {
                Identifier tierIdent = new Identifier(OpenComputersRewritten.identifier, "textures/icons/tier" + slot.tier() + ".png");
                drawAt(context, slot.x, slot.y, 16, 16, 16, 16, tierIdent);
            }
        }
    }

    @Override
    protected void drawSlot(DrawContext context, Slot slot) {
        if (slot instanceof ComponentSlot componentSlot) {
            drawSlot(context, componentSlot);
        }
        super.drawSlot(context, slot);
    }

    private void drawPowerButton(DrawContext context, int mouseX, int mouseY) {
        int u = 0, v = 0;
        if (mouseX > backgroundX+70 && mouseX < backgroundX+70+18 && mouseY > backgroundY+33 && mouseY < backgroundY+33+18) {
            v = 18;
        }
        if (handler instanceof CaseScreenHandler caseScreenHandler) {
            //caseScreenHandler.quickMove()
        }
        Identifier PowerIdent = new Identifier(OpenComputersRewritten.identifier, "textures/gui/button_power.png");
        drawAt(context, backgroundX + 70, backgroundY + 33, u, v, 18, 18, 36, 36, PowerIdent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawPowerButton(context, mouseX, mouseY);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        backgroundX = (width - backgroundWidth) / 2;
        backgroundY = (height - backgroundHeight) / 2;
    }
}
