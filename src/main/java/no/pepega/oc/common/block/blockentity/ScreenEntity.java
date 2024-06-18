package no.pepega.oc.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import no.pepega.oc.common.init.BlockEntities;

public class ScreenEntity extends ExtendedBlockEntity {
    private int tier;
    private String text = "Hello, Minecraft!";

    public ScreenEntity(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public ScreenEntity(BlockPos pos, BlockState state, int tier) {
        super(BlockEntities.SCREEN, pos, state);
        this.tier = tier;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
