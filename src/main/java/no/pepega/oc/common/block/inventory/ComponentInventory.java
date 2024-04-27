package no.pepega.oc.common.block.inventory;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface ComponentInventory extends EasyInventory {
    List<? extends ComponentSlot> getSlotTypes();

    @Override
    default void setStack(int slot, ItemStack stack) {
        if (getSlotTypes().get(slot).canInsert(stack)) {

        }
        getItems().set(slot, stack);
        if (stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
        }
    }
}
