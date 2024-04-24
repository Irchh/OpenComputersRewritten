package no.pepega.oc.common.block.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import no.pepega.oc.api.component.ComponentItem;
import no.pepega.oc.common.SlotType;
import no.pepega.oc.common.Tier;
import org.jetbrains.annotations.Nullable;

public abstract class ComponentSlot extends Slot {
    public abstract String slot();
    public abstract int tier();

    public ComponentSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Nullable
    @Override
    public Pair<Identifier, Identifier> getBackgroundSprite() {
        return super.getBackgroundSprite();
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (!inventory.getStack(getIndex()).isEmpty()) return false;
        if (slot() == SlotType.None || tier() == Tier.None) return false;
        if (slot() == SlotType.Any && tier() == Tier.Any) return true;
        // Special case: tool slots fit everything.
        if (slot() == SlotType.Tool) return true;
        if (stack.getItem() instanceof ComponentItem component) {
            boolean slotOk = (slot() == SlotType.Any || component.componentType() == slot());
            boolean tierOk = (tier() == Tier.Any || component.tier() <= tier());
            return slotOk && tierOk;
        }
        return false;
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        // TODO: implement this for later, only used for disassembler in original mod.
        //inventory match {
        //      case playerAware: common.tileentity.traits.PlayerInputAware =>
        //        playerAware.onSetInventorySlotContents(agentContainer.playerInventory.player, getSlotIndex, stack)
        //      case _ =>
        //    }
    }

    @Override
    public boolean isEnabled() {
        return slot() != SlotType.None && tier() != Tier.None && super.isEnabled();
    }
}
