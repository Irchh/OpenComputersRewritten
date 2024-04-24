package no.pepega.oc.common.block.inventory;

import net.minecraft.inventory.Inventory;
import no.pepega.oc.api.component.ComponentType;
import no.pepega.oc.common.InventorySlots;

public class StaticComponentSlot extends ComponentSlot {
    private final ComponentType slot;
    private final int tier;

    public StaticComponentSlot(Inventory inventory, int index, int x, int y, InventorySlots.InventorySlot slotType) {
        super(inventory, index, x, y);
        this.tier = slotType.tier;
        this.slot = slotType.slot;
    }

    @Override
    public ComponentType slotType() {
        return slot;
    }

    @Override
    public int tier() {
        return tier;
    }

    @Override
    public int getMaxItemCount() {
        return switch (slot) {
            case ComponentType.Tool, ComponentType.Any, ComponentType.Filtered -> super.getMaxItemCount();
            case ComponentType.None -> 0;
            default -> 1;
        };
    }
}
