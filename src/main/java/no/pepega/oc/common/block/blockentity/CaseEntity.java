package no.pepega.oc.common.block.blockentity;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import no.pepega.oc.api.machine.Machine;
import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.api.network.Message;
import no.pepega.oc.api.network.Node;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.block.Case;
import no.pepega.oc.common.block.inventory.EasyInventory;
import no.pepega.oc.common.block.util.PropertyRunning;
import no.pepega.oc.common.block.util.Rotatable;
import no.pepega.oc.common.init.BlockEntities;
import no.pepega.oc.common.ui.CaseScreenHandler;
import org.jetbrains.annotations.Nullable;

public class CaseEntity extends ExtendedBlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, EasyInventory, no.pepega.oc.api.internal.Case {
    DefaultedList<ItemStack> items;
    private boolean powered;
    public boolean powered() {
        return powered;
    }
    private int tier;

    public CaseEntity(BlockPos pos, BlockState state) {
        this(pos, state, Tier.Three);
    }

    public CaseEntity(BlockPos pos, BlockState state, int tier) {
        super(BlockEntities.CASE, pos, state);
        if (state.getBlock() instanceof Case c) {
            this.tier = c.tier();
            this.powered = state.get(PropertyRunning.Running);
        } else {
            this.tier = tier;
            this.powered = false;
        }
        items = switch (this.tier) {
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
        this.powered = nbt.getBoolean("powered");
        Inventories.readNbt(nbt, items, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, items, registryLookup);
        nbt.putBoolean("powered", this.powered);
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
        return new CaseScreenHandler(syncId, playerInventory, this, tier, pos);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }

    public void onPowerPressed() {
        if (world.getBlockState(pos).getBlock() instanceof Case c) {
            this.powered = c.onPowerChange(world, pos, world.getBlockState(pos));
            this.markDirty();
        }
    }

    // ----------------------------------------------------------------------- //


    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public void setColor(int value) {

    }

    @Override
    public boolean controlsConnectivity() {
        return false;
    }

    @Override
    public Direction facing() {
        return this.getCachedState().get(Rotatable.Facing);
    }

    @Override
    public Direction toGlobal(Direction value) {
        return switch (facing()) {
            case Direction.NORTH -> value.getOpposite();
            case Direction.EAST -> value.rotateYCounterclockwise();
            case Direction.SOUTH -> value;
            case Direction.WEST -> value.rotateYClockwise();
            default -> throw new IllegalStateException("facing() returned impossible value " + facing());
        };
    }

    @Override
    public Direction toLocal(Direction value) {
        return switch (facing()) {
            case Direction.NORTH -> value.getOpposite();
            case Direction.EAST -> value.rotateYClockwise();
            case Direction.SOUTH -> value;
            case Direction.WEST -> value.rotateYCounterclockwise();
            default -> throw new IllegalStateException("facing() returned impossible value " + facing());
        };
    }

    @Override
    public int tier() {
        return this.tier;
    }

    @Override
    public Machine machine() {
        return null;
    }

    @Override
    public Iterable<ItemStack> internalComponents() {
        return null;
    }

    @Override
    public int componentSlot(String address) {
        return 0;
    }

    @Override
    public void onMachineConnect(Node node) {

    }

    @Override
    public void onMachineDisconnect(Node node) {

    }

    @Override
    public Node node() {
        return null;
    }

    @Override
    public void onConnect(Node node) {

    }

    @Override
    public void onDisconnect(Node node) {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public PacketCodec<ByteBuf, EnvironmentHost> packet_codec() {
        return null;
    }

    @Override
    public World world() {
        return null;
    }

    @Override
    public double xPosition() {
        return 0;
    }

    @Override
    public double yPosition() {
        return 0;
    }

    @Override
    public double zPosition() {
        return 0;
    }

    @Override
    public void markChanged() {

    }

    public void tick(World world, BlockPos pos, BlockState state) {
        for (Direction direction : Direction.values()) {
            var neighbor = world.getBlockEntity(pos.offset(direction));
            if (neighbor instanceof ScreenEntity screen) {
                screen.setText(this.powered() ? "On" : "Off");
            }
        }
    }
}
