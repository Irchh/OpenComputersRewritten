package no.pepega.oc.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import no.pepega.oc.common.block.util.*;
import no.pepega.oc.util.Color;
import no.pepega.oc.util.Tooltip;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Case extends ExtendedBlock implements Tier, Colored {
    private final int tier;

    public Case(Settings settings, int tier) {
        super(settings);
        this.tier = tier;
        setDefaultState(getDefaultState().with(Rotatable.Facing, Direction.NORTH));
        setDefaultState(getDefaultState().with(PropertyRunning.Running, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Rotatable.Facing, PropertyRunning.Running);
        super.appendProperties(builder);
    }

    @Override
    public int tier() {
        return tier;
    }

    @Override
    public String registryName() {
        if (tier == 3) {
            return "casecreative";
        }
        return super.registryName() + (tier + 1);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Rotatable.Facing, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    // ----------------------------------------------------------------------- //


    @Override
    protected void tooltipBody(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        for (String curr: Tooltip.get(getClass().getSimpleName().toLowerCase(), slots())) {
            tooltip.add(Text.literal(curr).setStyle(Tooltip.DefaultStyle));
        }
    }

    private String slots() {
        return switch (tier) {
            case 0 -> "2/1/1";
            case 1 -> "2/2/2";
            case 2, 3 -> "3/2/3";
            default -> "0/0/0";
        };
    }

    @Override
    public DyeColor blockTint() {
        return Color.byTier.get(tier);
    }
}
