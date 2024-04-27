package no.pepega.oc.common.block.inventory;

import net.minecraft.inventory.Inventory;
import no.pepega.oc.api.driver.item.SlotType;
import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.common.InventorySlots;

public class StaticComponentSlot extends ComponentSlot {
    private final SlotType slot;
    private final int tier;

        public StaticComponentSlot(Inventory inventory, int index, int x, int y, InventorySlots.InventorySlot slotType, Class<? extends EnvironmentHost> host) {
        super(inventory, index, x, y, host);
        this.tier = slotType.tier;
        this.slot = slotType.slot;
    }

    @Override
    public SlotType slotType() {
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
