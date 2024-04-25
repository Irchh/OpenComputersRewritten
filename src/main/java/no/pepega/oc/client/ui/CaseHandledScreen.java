package no.pepega.oc.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.api.component.ComponentType;
import no.pepega.oc.common.block.blockentity.CaseEntity;
import no.pepega.oc.common.block.inventory.ComponentSlot;
import no.pepega.oc.common.networking.PowerButtonPressedPayload;
import no.pepega.oc.common.ui.CaseScreenHandler;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class CaseHandledScreen extends HandledScreen<CaseScreenHandler> {
    public CaseHandledScreen(CaseScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.literal("Computer"));
        if (handler instanceof CaseScreenHandler caseHandler) {
            BlockPos pos = caseHandler.getPos();
        }
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

    private boolean inPowerButton(double mouseX, double mouseY) {
        return mouseX > backgroundX+70 && mouseX < backgroundX+70+18 && mouseY > backgroundY+33 && mouseY < backgroundY+33+18;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && inPowerButton(mouseX, mouseY)) {
            if (client != null && client.world != null && client.world.getBlockEntity(handler.getPos()) instanceof CaseEntity caseEntity) {
                ClientPlayNetworking.send(new PowerButtonPressedPayload(handler.getPos()));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawPowerButton(DrawContext context, int mouseX, int mouseY) {
        int u = 0, v = 0;
        if (inPowerButton(mouseX, mouseY)) {
            v = 18;
        }
        if (client != null && client.world != null && client.world.getBlockEntity(handler.getPos()) instanceof CaseEntity caseEntity && caseEntity.powered()) {
            u = 18;
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
        super.init();
        titleX = playerInventoryTitleX;
        backgroundX = (width - backgroundWidth) / 2;
        backgroundY = (height - backgroundHeight) / 2;
    }
}
