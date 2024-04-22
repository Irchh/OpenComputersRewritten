package no.pepega.oc.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import no.pepega.oc.common.init.BlockEntities;

public class ScreenEntity extends RotatableBlockEntity {
    private int tier;

    public ScreenEntity(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public ScreenEntity(BlockPos pos, BlockState state, int tier) {
        super(BlockEntities.SCREEN, pos, state);
        this.tier = tier;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("tier", tier);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        tier = nbt.getInt("tier");
        super.readNbt(nbt, registryLookup);
    }
}
