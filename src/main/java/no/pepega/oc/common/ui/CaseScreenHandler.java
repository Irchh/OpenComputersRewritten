package no.pepega.oc.common.ui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import no.pepega.oc.common.InventorySlots;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.block.inventory.StaticComponentSlot;
import no.pepega.oc.common.init.GUIs;

import static no.pepega.oc.client.ui.CaseHandledScreen.slotSize;

public class CaseScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final int tier;

    public static CaseScreenHandler tier1(int syncId, PlayerInventory playerInventory) {
        return new CaseScreenHandler(syncId, playerInventory, new SimpleInventory(7), 0);
    }

    public static CaseScreenHandler tier2(int syncId, PlayerInventory playerInventory) {
        return new CaseScreenHandler(syncId, playerInventory, new SimpleInventory(8), 1);
    }

    public static CaseScreenHandler tier3(int syncId, PlayerInventory playerInventory) {
        return new CaseScreenHandler(syncId, playerInventory, new SimpleInventory(10), 2);
    }

    public static CaseScreenHandler creative(int syncId, PlayerInventory playerInventory) {
        return new CaseScreenHandler(syncId, playerInventory, new SimpleInventory(10), 3);
    }

    private int slotIndex = 0;
    private void addNewSlot(Inventory inventory, int x, int y, InventorySlots.InventorySlot slot) {
        this.addSlot(new StaticComponentSlot(inventory, slotIndex, x, y, slot));
        //System.out.printf("Adding slot (%s.%s) with index %s @ %s/%s%n", slot.slot, slot.tier, slotIndex, x, y);
        slotIndex += 1;
    }

    @Override
    public ScreenHandlerType<?> getType() {
        return switch (tier) {
            case 0 -> GUIs.CASE_SCREEN_HANDLER_TIER1;
            case 1 -> GUIs.CASE_SCREEN_HANDLER_TIER2;
            case 2 -> GUIs.CASE_SCREEN_HANDLER_TIER3;
            default -> GUIs.CASE_SCREEN_HANDLER_TIERC;
        };
    }

    public CaseScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, int tier) {
        super(null, syncId);
        this.tier = tier;
        this.inventory = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        // Add-in cards
        if (tier >= Tier.Three) {
            for (int i = 0; i <= 2; i++) {
                InventorySlots.InventorySlot slot = InventorySlots.computer.get(tier).get(slots.size());
                this.addNewSlot(inventory, 98, 16 + i * slotSize, slot);
            }
        } else {
            for (int i = 0; i <= 1; i++) {
                InventorySlots.InventorySlot slot = InventorySlots.computer.get(tier).get(slots.size());
                this.addNewSlot(inventory, 98, 16 + i * slotSize, slot);
            }
        }

        // Memory
        for (int i = 0; i <= (switch (tier) {case Tier.One -> 0;default -> 1;}); i++) {
            InventorySlots.InventorySlot slot = InventorySlots.computer.get(tier).get(slots.size());
            this.addNewSlot(inventory, 120, 16 + (i + 1) * slotSize, slot);
        }

        // HDD
        for (int i = 0; i <= (switch (tier) {case Tier.One -> 0;default -> 1;}); i++) {
            InventorySlots.InventorySlot slot = InventorySlots.computer.get(tier).get(slots.size());
            this.addNewSlot(inventory, 142, 16 + i * slotSize, slot);
        }

        // Floppy
        if (tier >= Tier.Three) {
            InventorySlots.InventorySlot slot = InventorySlots.computer.get(tier).get(slots.size());
            this.addNewSlot(inventory, 142, 16 + 2 * slotSize, slot);
        }

        // CPU
        {
            InventorySlots.InventorySlot slot = InventorySlots.computer.get(tier).get(slots.size());
            this.addNewSlot(inventory, 120, 16, slot);
        }

        // Another tier 1 memory slot. IDK why it is here
        if (tier == Tier.One) {
            InventorySlots.InventorySlot slot = InventorySlots.computer.get(tier).get(slots.size());
            this.addNewSlot(inventory, 120, 16 + 2 * slotSize, slot);
        }

        // EEPROM
        {
            InventorySlots.InventorySlot slot = InventorySlots.computer.get(tier).get(slots.size());
            this.addNewSlot(inventory, 48, 34, slot);
        }

        addPlayerInventorySlots(8, 84, playerInventory);
    }

    private void addPlayerInventorySlots(int left, int top, PlayerInventory playerInventory) {
        int playerInventorySizeX = Math.min(9, PlayerInventory.getHotbarSize());
        int playerInventorySizeY = Math.min(4, playerInventory.main.size() / playerInventorySizeX);

        // Show the inventory proper. Start at plus one to skip hot bar.
        for (int slotY = 1; slotY < playerInventorySizeY; slotY++) {
            for (int slotX = 0; slotX < playerInventorySizeX; slotX++) {
                int index = slotX + slotY * playerInventorySizeX;
                int x = left + slotX * slotSize;
                // Compensate for hot bar offset.
                int y = top + (slotY - 1) * slotSize;
                addSlot(new Slot(playerInventory, index, x, y));
            }
        }

        // Show the quick slot bar below the internal inventory.
        int quickBarSpacing = 4;
        for (int index = 0; index < playerInventorySizeX; index++) {
            int x = left + index * slotSize;
            int y = top + slotSize * (playerInventorySizeY - 1) + quickBarSpacing;
            addSlot(new Slot(playerInventory, index, x, y));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
