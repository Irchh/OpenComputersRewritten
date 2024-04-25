package no.pepega.oc.common.block.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import no.pepega.oc.api.component.ComponentItem;
import no.pepega.oc.api.component.ComponentType;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.item.util.CPULike;
import org.jetbrains.annotations.Nullable;

public abstract class ComponentSlot extends Slot {
    public abstract ComponentType slotType();
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
        if (slotType() == ComponentType.None || tier() == Tier.None) return false;
        if (slotType() == ComponentType.Any && tier() == Tier.Any) return true;
        // Special case: tool slots fit everything.
        if (slotType() == ComponentType.Tool) return true;
        if (stack.getItem() instanceof ComponentItem component) {
            boolean slotOk = (slotType() == ComponentType.Any || component.componentType() == slotType());
            if (slotType() == ComponentType.CPU && component instanceof CPULike cpu) {
                boolean tierOk = (tier() == Tier.Any || cpu.cpuTier() <= tier());
                return slotOk && tierOk;
            } else {
                boolean tierOk = (tier() == Tier.Any || component.tier() <= tier());
                return slotOk && tierOk;
            }
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
        return slotType() != ComponentType.None && tier() != Tier.None && super.isEnabled();
    }
}
