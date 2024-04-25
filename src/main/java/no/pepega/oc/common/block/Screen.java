package no.pepega.oc.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import no.pepega.oc.OCSettings;
import no.pepega.oc.common.block.blockentity.ScreenEntity;
import no.pepega.oc.common.block.util.Colored;
import no.pepega.oc.common.block.util.ExtendedBlock;
import no.pepega.oc.common.block.util.Rotatable;
import no.pepega.oc.common.block.util.Tier;
import no.pepega.oc.util.Color;
import no.pepega.oc.util.PackedColor;
import no.pepega.oc.util.Tooltip;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Screen extends ExtendedBlock implements Tier, Colored, BlockEntityProvider {
    private final int tier;

    public Screen(Settings settings, int tier) {
        super(settings);
        this.tier = tier;
        setDefaultState(getDefaultState().with(Rotatable.Pitch, Direction.NORTH));
        setDefaultState(getDefaultState().with(Rotatable.Yaw, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Rotatable.Pitch, Rotatable.Yaw);
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ScreenEntity(pos, state, tier);
    }

    @Override
    public int tier() {
        return tier;
    }

    @Override
    public String registryName() {
        return super.registryName() + (tier + 1);
    }

    @Override
    protected void tooltipBody(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        int w = OCSettings.screenResolutionsByTier.get(tier).getKey();
        int h = OCSettings.screenResolutionsByTier.get(tier).getValue();
        int depth = PackedColor.Depth.bits(OCSettings.screenDepthsByTier[tier]);
        for (String curr: Tooltip.get(getClass().getSimpleName().toLowerCase(), w, h, depth)) {
            tooltip.add(Text.literal(curr).setStyle(Tooltip.DefaultStyle));
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (Math.abs(ctx.getPlayer().getPitch()) < 50.0) {
            return super.getPlacementState(ctx)
                    .with(Rotatable.Yaw, ctx.getHorizontalPlayerFacing().getOpposite())
                    .with(Rotatable.Pitch, Direction.NORTH);
        } else {
            return super.getPlacementState(ctx)
                    .with(Rotatable.Yaw, ctx.getHorizontalPlayerFacing().getOpposite())
                    .with(Rotatable.Pitch, ctx.getVerticalPlayerLookDirection().getOpposite());
        }
    }

    @Override
    public DyeColor blockTint() {
        return Color.byTier.get(tier);
    }
}
