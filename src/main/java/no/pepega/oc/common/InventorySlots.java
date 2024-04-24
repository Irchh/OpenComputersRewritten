package no.pepega.oc.common;

import no.pepega.oc.api.component.ComponentType;

import java.util.Arrays;
import java.util.List;

public class InventorySlots {
    public static List<List<InventorySlot>> computer = Arrays.asList(
            Arrays.asList(
                    new InventorySlot(ComponentType.Card, Tier.One),
                    new InventorySlot(ComponentType.Card, Tier.One),
                    new InventorySlot(ComponentType.Memory, Tier.One),
                    new InventorySlot(ComponentType.HDD, Tier.One),
                    new InventorySlot(ComponentType.CPU, Tier.One),
                    new InventorySlot(ComponentType.Memory, Tier.One),
                    new InventorySlot(ComponentType.EEPROM, Tier.Any)
            ),

            Arrays.asList(
                    new InventorySlot(ComponentType.Card, Tier.Two),
                    new InventorySlot(ComponentType.Card, Tier.One),
                    new InventorySlot(ComponentType.Memory, Tier.Two),
                    new InventorySlot(ComponentType.Memory, Tier.Two),
                    new InventorySlot(ComponentType.HDD, Tier.Two),
                    new InventorySlot(ComponentType.HDD, Tier.One),
                    new InventorySlot(ComponentType.CPU, Tier.Two),
                    new InventorySlot(ComponentType.EEPROM, Tier.Any)
            ),

            Arrays.asList(
                    new InventorySlot(ComponentType.Card, Tier.Three),
                    new InventorySlot(ComponentType.Card, Tier.Two),
                    new InventorySlot(ComponentType.Card, Tier.Two),
                    new InventorySlot(ComponentType.Memory, Tier.Three),
                    new InventorySlot(ComponentType.Memory, Tier.Three),
                    new InventorySlot(ComponentType.HDD, Tier.Three),
                    new InventorySlot(ComponentType.HDD, Tier.Two),
                    new InventorySlot(ComponentType.Floppy, Tier.One),
                    new InventorySlot(ComponentType.CPU, Tier.Three),
                    new InventorySlot(ComponentType.EEPROM, Tier.Any)
            ),

            Arrays.asList(
                    new InventorySlot(ComponentType.Card, Tier.Three),
                    new InventorySlot(ComponentType.Card, Tier.Three),
                    new InventorySlot(ComponentType.Card, Tier.Three),
                    new InventorySlot(ComponentType.Memory, Tier.Three),
                    new InventorySlot(ComponentType.Memory, Tier.Three),
                    new InventorySlot(ComponentType.HDD, Tier.Three),
                    new InventorySlot(ComponentType.HDD, Tier.Three),
                    new InventorySlot(ComponentType.Floppy, Tier.One),
                    new InventorySlot(ComponentType.CPU, Tier.Three),
                    new InventorySlot(ComponentType.EEPROM, Tier.Any)
            )
    );

    public static class InventorySlot {
        public final ComponentType slot;
        public final int tier;

        public InventorySlot(ComponentType slot, int tier) {
            this.slot = slot;
            this.tier = tier;
        }
    }
}
