package no.pepega.oc.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.common.item.EEPROM;
import no.pepega.oc.common.item.Memory;
import no.pepega.oc.common.item.util.ExtendedItem;

public class Items {
    public static final EEPROM eeprom =  new EEPROM(new Item.Settings());
    public static final Memory memory0 = new Memory(new Item.Settings(), 0);
    public static final Memory memory1 = new Memory(new Item.Settings(), 1);
    public static final Memory memory2 = new Memory(new Item.Settings(), 2);
    public static final Memory memory3 = new Memory(new Item.Settings(), 3);
    public static final Memory memory4 = new Memory(new Item.Settings(), 4);
    public static final Memory memory5 = new Memory(new Item.Settings(), 5);

    public interface ItemInterface {
        void onItem(ExtendedItem item);
    }

    public static void runOnAllItems(ItemInterface onItem) {
        onItem.onItem(eeprom);
        onItem.onItem(memory0);
        onItem.onItem(memory1);
        onItem.onItem(memory2);
        onItem.onItem(memory3);
        onItem.onItem(memory4);
        onItem.onItem(memory5);
    }

    public static void init() {
        runOnAllItems(Items::register);
    }

    private static <T extends ExtendedItem> T register(T item) {
        return Registry.register(Registries.ITEM, new Identifier(OpenComputersRewritten.identifier, item.registryName()), item);
    }
}
