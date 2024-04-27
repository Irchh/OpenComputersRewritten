package no.pepega.oc.common.block.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import no.pepega.oc.api.Driver;
import no.pepega.oc.api.driver.item.SlotType;
import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.common.Tier;
import org.jetbrains.annotations.Nullable;

public abstract class ComponentSlot extends Slot {
    private final Class<? extends EnvironmentHost> host;

    public abstract SlotType slotType();
    public abstract int tier();

    public ComponentSlot(Inventory inventory, int index, int x, int y, Class<? extends EnvironmentHost> host) {
        super(inventory, index, x, y);
        this.host = host;
    }

    @Nullable
    @Override
    public Pair<Identifier, Identifier> getBackgroundSprite() {
        return super.getBackgroundSprite();
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (!inventory.getStack(getIndex()).isEmpty()) return false;
        if (slotType() == SlotType.None || tier() == Tier.None) return false;
        if (slotType() == SlotType.Any && tier() == Tier.Any) return true;
        // Special case: tool slots fit everything.
        if (slotType() == SlotType.Tool) return true;
        var driver = switch (host) {
            case null -> Driver.driverFor(stack);
            default -> Driver.driverFor(stack, host);
        };
        if (driver != null) {
            var slotOk = (slotType() == SlotType.Any || driver.slot(stack) == slotType());
            var tierOk = (tier() == Tier.Any || driver.tier(stack) <= tier());
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
        return slotType() != SlotType.None && tier() != Tier.None && super.isEnabled();
    }
}
