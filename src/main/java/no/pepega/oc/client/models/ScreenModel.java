package no.pepega.oc.client.models;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import no.pepega.oc.client.Textures;
import no.pepega.oc.common.block.Screen;
import no.pepega.oc.common.block.util.Rotatable;
import no.pepega.oc.util.Color;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ScreenModel extends BaseModel {
    List<Sprite> sprites = new ArrayList<>();
    List<Sprite> spritesFront = new ArrayList<>();

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        for(int i = 0; i < Textures.screen.Single.size(); ++i) {
            sprites.add(textureGetter.apply(Textures.screen.Single.get(i)));
        }
        for(int i = 0; i < Textures.screen.SingleFront.size(); ++i) {
            spritesFront.add(textureGetter.apply(Textures.screen.SingleFront.get(i)));
        }
        return this;
    }

    @Override
    public Sprite getParticleSprite() {
        return sprites.getFirst();
    }

    private void buildMesh(Direction facing, int pitch, QuadEmitter emitter, int color) {
        for(Direction direction : Direction.values()) {
            // Get the right sprite depending on which way we are facing
            Sprite sprite;
            if (!direction.equals(facing)) {
                sprite = sprites.get(direction.getId());
            } else {
                sprite = spritesFront.get(pitch);
            }
            // Add a new face to the mesh
            emitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
            // Set the sprite of the face, must be called after .square()
            // We haven't specified any UV coordinates, so we want to use the whole texture. BAKE_LOCK_UV does exactly that.
            emitter.spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV);
            // Change color based on the block state
            emitter.color(color, color, color, color);
            // Add the quad to the mesh
            emitter.emit();
        }
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        QuadEmitter emitter = context.getEmitter();
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();

        int pitch = 1;
        Direction facing = state.get(Rotatable.Yaw);
        if (state.get(Rotatable.Pitch) == Direction.NORTH) {
            pitch = 0;
        } else {
            facing = state.get(Rotatable.Pitch);
        }
        int color = Color.rgbValues.get(((Screen)state.getBlock()).blockTint());

        buildMesh(facing, pitch, emitter, color);

        mesh = builder.build();
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        QuadEmitter emitter = context.getEmitter();
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();

        int pitch = 0;
        Direction facing = Direction.NORTH;

        int color = 0xFFFFFF;
        if (stack.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof Screen screen) {
                color = Color.rgbValues.get(Color.byTier.get(screen.tier()));
            }
        }

        buildMesh(facing, pitch, emitter, color);

        mesh = builder.build();

        super.emitItemQuads(stack, randomSupplier, context);
    }

    /*
    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState blockState, BlockPos pos, Supplier<Random> supplier, RenderContext renderContext) {
        QuadEmitter emitter = renderContext.getEmitter();
        for(Direction direction : Direction.values()) {
            // UP and DOWN share the Y axis
            int spriteIdx = direction.getId();
            // Add a new face to the mesh
            emitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
            // Set the sprite of the face, must be called after .square()
            // We haven't specified any UV coordinates, so we want to use the whole texture. BAKE_LOCK_UV does exactly that.
            emitter.spriteBake(sprites[spriteIdx], MutableQuadView.BAKE_LOCK_UV);
            // Enable texture usage
            emitter.color(-1, -1, -1, -1);
            // Add the quad to the mesh
            emitter.emit();
        }
        super.emitBlockQuads(blockView, blockState, pos, supplier, renderContext);
    }*/
}
