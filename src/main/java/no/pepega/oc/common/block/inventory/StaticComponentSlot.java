package no.pepega.oc.common.block.inventory;

import net.minecraft.inventory.Inventory;
import no.pepega.oc.common.InventorySlots;
import no.pepega.oc.common.SlotType;

public class StaticComponentSlot extends ComponentSlot {
    private final String slot;
    private final int tier;

    public StaticComponentSlot(Inventory inventory, int index, int x, int y, InventorySlots.InventorySlot slotType) {
        super(inventory, index, x, y);
        this.tier = slotType.tier;
        this.slot = slotType.slot;
    }

    @Override
    public String slot() {
        return slot;
    }

    @Override
    public int tier() {
        return tier;
    }

    @Override
    public int getMaxItemCount() {
        return switch (slot) {
            case SlotType.Tool, SlotType.Any, SlotType.Filtered -> super.getMaxItemCount();
            case SlotType.None -> 0;
            default -> 1;
        };
    }
}
