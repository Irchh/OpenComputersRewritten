package no.pepega.oc.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.block.Case;
import no.pepega.oc.common.block.inventory.EasyInventory;
import no.pepega.oc.common.init.BlockEntities;
import no.pepega.oc.common.ui.CaseScreenHandler;
import org.jetbrains.annotations.Nullable;

public class CaseEntity extends ExtendedBlockEntity implements NamedScreenHandlerFactory, EasyInventory {
    DefaultedList<ItemStack> items;
    private int tier;

    public CaseEntity(BlockPos pos, BlockState state) {
        this(pos, state, Tier.Three);
    }

    public CaseEntity(BlockPos pos, BlockState state, int tier) {
        super(BlockEntities.CASE, pos, state);
        if (state.getBlock() instanceof Case c) {
            this.tier = c.tier();
        } else {
            this.tier = tier;
        }
        items = switch (tier) {
            case Tier.One -> DefaultedList.ofSize(7, ItemStack.EMPTY);
            case Tier.Two -> DefaultedList.ofSize(8, ItemStack.EMPTY);
            default -> DefaultedList.ofSize(10, ItemStack.EMPTY);
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    // Save inventory and state
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, items, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, items, registryLookup);
        super.writeNbt(nbt, registryLookup);
    }

    // Menu stuff

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CaseScreenHandler(syncId, playerInventory, this, tier);
    }
}
