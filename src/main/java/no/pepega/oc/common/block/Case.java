package no.pepega.oc.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import no.pepega.oc.api.internal.Tiered;
import no.pepega.oc.common.block.blockentity.CaseEntity;
import no.pepega.oc.common.block.util.*;
import no.pepega.oc.util.Color;
import no.pepega.oc.util.Tooltip;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Case extends ExtendedBlock implements Tiered, Colored, BlockEntityProvider {
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

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CaseEntity(pos, state, tier);
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

    // For interacting with the inventory
    @Override
    @Nullable
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)((Object)blockEntity) : null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            //This will call the createScreenHandlerFactory method from above, which will return our blockEntity casted to
            //a namedScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, blockPos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    public boolean onPowerChange(World world, BlockPos pos, BlockState state) {
        var isRunning = state.get(PropertyRunning.Running);
        world.setBlockState(pos, state.with(PropertyRunning.Running, !isRunning));
        return !isRunning;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CaseEntity caseEntity) {
                ItemScatterer.spawn(world, pos, caseEntity);
                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        // TODO: Implement maybe
        return super.hasComparatorOutput(state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (blockEntityWorld, pos, blockState, be) -> {
            if (be instanceof CaseEntity ce) {
                ce.tick(blockEntityWorld, pos, blockState);
            }
        };
    }
}
