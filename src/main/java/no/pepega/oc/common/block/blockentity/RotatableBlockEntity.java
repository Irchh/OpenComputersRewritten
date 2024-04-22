package no.pepega.oc.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class RotatableBlockEntity extends ExtendedBlockEntity {
    public RotatableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // ----------------------------------------------------------------------- //
    // Lookup tables
    // ----------------------------------------------------------------------- //

    private List<Direction> pitch2Direction = Arrays.asList(Direction.UP, Direction.NORTH, Direction.DOWN);

    private List<Direction> yaw2Direction = Arrays.asList(Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST);


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onPlaced(world, pos, state, placer, stack);
    }
}
