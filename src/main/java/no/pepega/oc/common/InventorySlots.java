package no.pepega.oc.common;

import no.pepega.oc.api.driver.item.SlotType;

import java.util.Arrays;
import java.util.List;

public class InventorySlots {
    public static List<List<InventorySlot>> computer = Arrays.asList(
            Arrays.asList(
                    new InventorySlot(SlotType.Card, Tier.One),
                    new InventorySlot(SlotType.Card, Tier.One),
                    new InventorySlot(SlotType.Memory, Tier.One),
                    new InventorySlot(SlotType.HDD, Tier.One),
                    new InventorySlot(SlotType.CPU, Tier.One),
                    new InventorySlot(SlotType.Memory, Tier.One),
                    new InventorySlot(SlotType.EEPROM, Tier.Any)
            ),

            Arrays.asList(
                    new InventorySlot(SlotType.Card, Tier.Two),
                    new InventorySlot(SlotType.Card, Tier.One),
                    new InventorySlot(SlotType.Memory, Tier.Two),
                    new InventorySlot(SlotType.Memory, Tier.Two),
                    new InventorySlot(SlotType.HDD, Tier.Two),
                    new InventorySlot(SlotType.HDD, Tier.One),
                    new InventorySlot(SlotType.CPU, Tier.Two),
                    new InventorySlot(SlotType.EEPROM, Tier.Any)
            ),

            Arrays.asList(
                    new InventorySlot(SlotType.Card, Tier.Three),
                    new InventorySlot(SlotType.Card, Tier.Two),
                    new InventorySlot(SlotType.Card, Tier.Two),
                    new InventorySlot(SlotType.Memory, Tier.Three),
                    new InventorySlot(SlotType.Memory, Tier.Three),
                    new InventorySlot(SlotType.HDD, Tier.Three),
                    new InventorySlot(SlotType.HDD, Tier.Two),
                    new InventorySlot(SlotType.Floppy, Tier.One),
                    new InventorySlot(SlotType.CPU, Tier.Three),
                    new InventorySlot(SlotType.EEPROM, Tier.Any)
            ),

            Arrays.asList(
                    new InventorySlot(SlotType.Card, Tier.Three),
                    new InventorySlot(SlotType.Card, Tier.Three),
                    new InventorySlot(SlotType.Card, Tier.Three),
                    new InventorySlot(SlotType.Memory, Tier.Three),
                    new InventorySlot(SlotType.Memory, Tier.Three),
                    new InventorySlot(SlotType.HDD, Tier.Three),
                    new InventorySlot(SlotType.HDD, Tier.Three),
                    new InventorySlot(SlotType.Floppy, Tier.One),
                    new InventorySlot(SlotType.CPU, Tier.Three),
                    new InventorySlot(SlotType.EEPROM, Tier.Any)
            )
    );

    public static class InventorySlot {
        public final SlotType slot;
        public final int tier;

        public InventorySlot(SlotType slot, int tier) {
            this.slot = slot;
            this.tier = tier;
        }
    }
}
