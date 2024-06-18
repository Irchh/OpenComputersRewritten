package no.pepega.oc.common.init;

import net.minecraft.block.Block;
import no.pepega.oc.common.block.Case;
import no.pepega.oc.common.block.util.ExtendedBlock;
import no.pepega.oc.common.block.Screen;

import static no.pepega.oc.api.API.items;

public class Blocks {
    public static final Case case1 = new Case(Block.Settings.create().strength(4.0f), 0);
    public static final Case case2 = new Case(Block.Settings.create().strength(4.0f), 1);
    public static final Case case3 = new Case(Block.Settings.create().strength(4.0f), 2);
    public static final Case case4 = new Case(Block.Settings.create().strength(4.0f), 3);
    public static final Screen screen1 = new Screen(Block.Settings.create().strength(4.0f), 0);
    public static final Screen screen2 = new Screen(Block.Settings.create().strength(4.0f), 1);
    public static final Screen screen3 = new Screen(Block.Settings.create().strength(4.0f), 2);

    public interface BlockInterface {
        void onBlock(ExtendedBlock item);
    }

    public static void runOnAllBlocks(BlockInterface onBlock) {
        // Order based on original mod
        onBlock.onBlock(case1);
        onBlock.onBlock(case2);
        onBlock.onBlock(case3);
        onBlock.onBlock(screen1);
        onBlock.onBlock(screen2);
        onBlock.onBlock(screen3);
        onBlock.onBlock(case4);
    }

    public static void init() {
        runOnAllBlocks(items::registerBlock);
    }
}
