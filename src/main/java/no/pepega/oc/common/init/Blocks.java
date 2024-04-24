package no.pepega.oc.common.init;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.common.block.Case;
import no.pepega.oc.common.block.util.ExtendedBlock;
import no.pepega.oc.common.block.Screen;
import no.pepega.oc.common.block.util.ExtendedBlockItem;
import no.pepega.oc.util.Color;

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
        onBlock.onBlock(case1);
        onBlock.onBlock(case2);
        onBlock.onBlock(case3);
        onBlock.onBlock(case4);
        onBlock.onBlock(screen1);
        onBlock.onBlock(screen2);
        onBlock.onBlock(screen3);
    }

    public static void init() {
        runOnAllBlocks(Blocks::register);
    }

    private static <T extends ExtendedBlock> void register(T block) {
        if (block instanceof Case computerCase) {
            ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
                if (view == null || view.getBlockEntityRenderData(pos) == null) return Color.rgbValues.get(computerCase.blockTint());
                return (Integer)view.getBlockEntityRenderData(pos);
            }, computerCase);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Color.rgbValues.get(computerCase.blockTint()), computerCase);
        }
        Registry.register(Registries.BLOCK, new Identifier(OpenComputersRewritten.identifier, block.registryName()), block);
        Registry.register(Registries.ITEM, new Identifier(OpenComputersRewritten.identifier, block.registryName()), new ExtendedBlockItem(block, new Item.Settings()));
    }
}
