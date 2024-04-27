package no.pepega.oc.integration.opencomputers;

import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import no.pepega.oc.OCSettings;
import no.pepega.oc.api.detail.ItemInfo;
import no.pepega.oc.api.driver.DriverItem;
import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.init.OCDataComponentTypes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class Item implements DriverItem {

    @Override
    public boolean worksWith(ItemStack stack) {

        return false;
    }

    @Override
    public int tier(ItemStack stack) {
        return Tier.One;
    }

    protected boolean isOneOf(ItemStack stack, ItemInfo...items) { return Arrays.stream(items).filter(Objects::nonNull).toList().contains(no.pepega.oc.api.Items.get(stack)); }
    protected boolean isAdapter(Class<? extends EnvironmentHost> host) { return no.pepega.oc.api.internal.Adapter.class.isAssignableFrom(host); }
    protected boolean isComputer(Class<? extends EnvironmentHost> host) { return no.pepega.oc.api.internal.Case.class.isAssignableFrom(host); }
    protected boolean isRobot(Class<? extends EnvironmentHost> host) { return no.pepega.oc.api.internal.Robot.class.isAssignableFrom(host); }
    protected boolean isRotatable(Class<? extends EnvironmentHost> host) { return no.pepega.oc.api.internal.Rotatable.class.isAssignableFrom(host); }
    protected boolean isServer(Class<? extends EnvironmentHost> host) { return no.pepega.oc.api.internal.Server.class.isAssignableFrom(host); }
    protected boolean isTablet(Class<? extends EnvironmentHost> host) { return no.pepega.oc.api.internal.Tablet.class.isAssignableFrom(host); }
    protected boolean isMicrocontroller(Class<? extends EnvironmentHost> host) { return no.pepega.oc.api.internal.Microcontroller.class.isAssignableFrom(host); }
    protected boolean isDrone(Class<? extends EnvironmentHost> host) { return no.pepega.oc.api.internal.Drone.class.isAssignableFrom(host); }

    @Override
    public NbtCompound dataTag(ItemStack stack) {
        NbtComponent nbtComponent = stack.get(OCDataComponentTypes.LEGACY_NBT_COMPAT);
        if (nbtComponent == null) {
            stack.set(OCDataComponentTypes.LEGACY_NBT_COMPAT, NbtComponent.DEFAULT);
        }
        NbtCompound nbt = nbtComponent.copyNbt();
        if (!nbt.contains(OCSettings.namespace + "data")) {
            nbt.put(OCSettings.namespace + "data", new NbtCompound());
        }
        NbtComponent.set(OCDataComponentTypes.LEGACY_NBT_COMPAT, stack, nbt);
        return stack.get(OCDataComponentTypes.LEGACY_NBT_COMPAT).copyNbt();
    }

    private static Optional<NbtCompound> getTag(NbtCompound tagCompound, List<String> keys) {
        if (keys.isEmpty())
            return Optional.of(tagCompound);
        if (!tagCompound.contains(keys.getFirst()))
            return Optional.empty();
        keys.removeFirst();
        return getTag(tagCompound.getCompound(keys.getFirst()), keys.subList(1, keys.size()));
    }

    private static Optional<NbtCompound> getTag(ItemStack stack, List<String> keys) {
        if (stack == null || stack.isEmpty())
            return Optional.empty();
        else {
            var nbtComponent = stack.get(OCDataComponentTypes.LEGACY_NBT_COMPAT);
            if (nbtComponent != null) {
                return getTag(nbtComponent.copyNbt(), keys);
            } else {
                return Optional.empty();
            }
        }
    }

    public static Optional<String> address(ItemStack stack) {
        var addressKey = "address";
        var tag = getTag(stack, Arrays.asList(OCSettings.namespace + "data", "node"));
        if (tag.isPresent() && tag.get().contains(addressKey)) {
            return Optional.of(tag.get().getString(addressKey));
        }
        return Optional.empty();
    }
}
