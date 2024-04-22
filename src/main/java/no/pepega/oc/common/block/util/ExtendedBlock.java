package no.pepega.oc.common.block.util;

import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import no.pepega.oc.common.block.blockentity.ExtendedBlockEntity;
import no.pepega.oc.util.Color;
import no.pepega.oc.util.Tooltip;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExtendedBlock extends Block implements RenderDataBlockEntity {
    public ExtendedBlock(Settings settings) {
        super(settings);
    }

    public String registryName() {
        return unlocalizedName();
    }

    public String unlocalizedName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.hasBlockEntity()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof ExtendedBlockEntity) {
                return ((ExtendedBlockEntity) be).onUse(state, world, pos, player, hit);
            }
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (state.hasBlockEntity()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof ExtendedBlockEntity) {
                ((ExtendedBlockEntity) be).onPlaced(world, pos, state, placer, stack);
            }
        }
        super.onPlaced(world, pos, state, placer, stack);
    }

    // ----------------------------------------------------------------------- //
    // BlockItem
    // ----------------------------------------------------------------------- //

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltipHead(stack, context, tooltip, options);
        tooltipBody(stack, context, tooltip, options);
        tooltipTail(stack, context, tooltip, options);
    }

    protected void tooltipHead(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
    }

    protected void tooltipBody(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        for (String curr: Tooltip.get(getClass().getSimpleName().toLowerCase())) {
            tooltip.add(Text.literal(curr).setStyle(Tooltip.DefaultStyle));
        }
    }

    protected void tooltipTail(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
    }

    @Override
    public @Nullable Object getRenderData() {
        if (this instanceof Colored colored) {
            DyeColor col = colored.blockTint();
            return Color.rgbValues.get(col);
        } else {
            return null;
        }
    }
}
