package no.pepega.oc.client.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import no.pepega.oc.common.block.blockentity.ScreenEntity;
import no.pepega.oc.common.block.util.Rotatable;
import org.joml.Quaternionf;

public class ScreenBlockEntityRenderer implements BlockEntityRenderer<ScreenEntity> {
    private final TextRenderer textRenderer;

    private static final Quaternionf rotate180 = new Quaternionf(0, 1, 0, 0);
    private static final Quaternionf rotate90 = new Quaternionf(0, 0.7071068, 0, 0.7071068);
    private static final Quaternionf rotate270 = new Quaternionf(0, -0.7071068, 0, 0.7071068);

    private static final Quaternionf rotateDown = new Quaternionf(0.7071068, 0, 0, 0.7071068);
    private static final Quaternionf rotateUp = new Quaternionf(-0.7071068, 0, 0, 0.7071068);

    public ScreenBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        textRenderer = ctx.getTextRenderer();
    }

    public float getTextScale() {
        return 0.3333333f;
    }

    @Override
    public void render(ScreenEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        var block = entity.getCachedState();
        switch (block.get(Rotatable.Yaw)) {
            case NORTH -> matrices.multiply(rotate180, 0.5f, 0.5f, 0.5f);
            case EAST -> matrices.multiply(rotate90, 0.5f, 0.5f, 0.5f);
            case WEST -> matrices.multiply(rotate270, 0.5f, 0.5f, 0.5f);
        }
        switch (block.get(Rotatable.Pitch)) {
            case UP -> matrices.multiply(rotateUp, 0.5f, 0.5f, 0.5f);
            case DOWN -> matrices.multiply(rotateDown, 0.5f, 0.5f, 0.5f);
        }
        matrices.translate(0.0f, 1.0f, 1.0f);
        matrices.translate(2.0f/16.0f, -2.0f/16.0f, 0.0f);
        float f = 0.015625f * this.getTextScale();
        matrices.scale(f, -f, f);
        this.textRenderer.draw(Text.of(entity.getText()), 0f, 0f, 0xFFFFFF, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, 255);
        matrices.pop();
    }
}
