package no.pepega.oc.client;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.common.init.Blocks;
import no.pepega.oc.common.init.Items;

import static no.pepega.oc.common.init.Blocks.case1;

public class OCItemGroup {
    private static final net.minecraft.item.ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(case1))
            .displayName(Text.translatable("itemGroup.OpenComputers"))
            .entries(OCItemGroup::populateEntries)
            .build();

    private static void populateEntries(ItemGroup.DisplayContext context, ItemGroup.Entries entries) {
        Blocks.runOnAllBlocks(entries::add);
        Items.runOnAllItems(entries::add);
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(OpenComputersRewritten.identifier, "itemgroup"), ITEM_GROUP);
    }
}
