package no.pepega.oc.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.common.block.blockentity.CaseEntity;
import no.pepega.oc.common.block.blockentity.ExtendedBlockEntity;
import no.pepega.oc.common.block.blockentity.ScreenEntity;

public class BlockEntities {
    public static BlockEntityType<ScreenEntity> SCREEN;
    public static BlockEntityType<CaseEntity> CASE;

    public static void init() {
        SCREEN = register(ScreenEntity::new, "screen", Blocks.screen1, Blocks.screen2, Blocks.screen3);
        CASE = register(CaseEntity::new, "screen", Blocks.case1, Blocks.case2, Blocks.case3, Blocks.case4);
    }

    private static <T extends ExtendedBlockEntity> BlockEntityType<T> register(BlockEntityType.BlockEntityFactory<T> blockEntity, String path, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(OpenComputersRewritten.identifier, path), BlockEntityType.Builder.create(blockEntity, blocks).build());
    }
}
