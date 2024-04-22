package no.pepega.oc.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import no.pepega.oc.common.init.BlockEntities;

public class ScreenEntity extends ExtendedBlockEntity {
    private int tier;

    public ScreenEntity(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public ScreenEntity(BlockPos pos, BlockState state, int tier) {
        super(BlockEntities.SCREEN, pos, state);
        this.tier = tier;
    }
}
