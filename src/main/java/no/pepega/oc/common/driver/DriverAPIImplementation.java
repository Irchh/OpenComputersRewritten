package no.pepega.oc.common.driver;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.api.detail.DriverAPI;
import no.pepega.oc.api.driver.*;
import no.pepega.oc.api.driver.item.HostAware;
import no.pepega.oc.api.network.EnvironmentHost;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class DriverAPIImplementation implements DriverAPI {
    private final List<DriverBlock> sidedBlocks = new ArrayList<>();

    private final List<DriverItem> items = new ArrayList<>();

    private final List<Converter> converters = new ArrayList<>();

    private final List<EnvironmentProvider> environmentProviders = new ArrayList<>();

    private final List<InventoryProvider> inventoryProviders = new ArrayList<>();

    private final List<Pair<ItemStack, Set<Class<?>>>> blacklist = new ArrayList<>();

    /** Used to keep track of whether we're past the init phase. */
    private boolean locked = false;

    @Override
    public void add(DriverBlock driver) {
        if (locked) throw new IllegalStateException("Please register all drivers in the init phase.");
        if (!sidedBlocks.contains(driver)) {
            OpenComputersRewritten.log.debug(String.format("Registering block driver %s.", driver.getClass().getName()));
            sidedBlocks.add(driver);
        }
    }

    @Override
    public void add(DriverItem driver) {
        if (locked) throw new IllegalStateException("Please register all drivers in the init phase.");
        if (!items.contains(driver)) {
            OpenComputersRewritten.log.debug(String.format("Registering item driver %s.", driver.getClass().getName()));
            items.add(driver);
        }
    }

    @Override
    public void add(Converter converter) {
        if (locked) throw new IllegalStateException("Please register all converters in the init phase.");
        if (!converters.contains(converter)) {
            OpenComputersRewritten.log.debug(String.format("Registering converter %s.", converter.getClass().getName()));
            converters.add(converter);
        }
    }

    @Override
    public void add(EnvironmentProvider provider) {
        if (locked) throw new IllegalStateException("Please register all environment providers in the init phase.");
        if (!environmentProviders.contains(provider)) {
            OpenComputersRewritten.log.debug(String.format("Registering environment provider %s.", provider.getClass().getName()));
            environmentProviders.add(provider);
        }
    }

    @Override
    public void add(InventoryProvider provider) {
        if (locked) throw new IllegalStateException("Please register all inventory providers in the init phase.");
        if (!inventoryProviders.contains(provider)) {
            OpenComputersRewritten.log.debug(String.format("Registering inventory provider %s.", provider.getClass().getName()));
            inventoryProviders.add(provider);
        }
    }

    @Override
    public @Nullable DriverBlock driverFor(World world, BlockPos pos, Direction side) {
        var sidedDrivers = sidedBlocks.stream().filter(driverBlock -> driverBlock.worksWith(world, pos, side)).toList();
        if (sidedDrivers.isEmpty())
            return null;
        else
            return new CompoundBlockDriver(sidedDrivers);
    }

    @Override
    public @Nullable DriverItem driverFor(ItemStack stack, Class<? extends EnvironmentHost> host) {
        if (!stack.isEmpty()) {
            var hostAware = items.stream().filter(i -> i instanceof HostAware).map(i -> (HostAware)i).toList();
            if (!hostAware.isEmpty()) {
                return hostAware.stream().filter(h -> h.worksWith(stack, host)).findFirst().orElse(null);
            } else {
                return driverFor(stack);
            }
        }
        return null;
    }

    @Override
    public @Nullable DriverItem driverFor(ItemStack stack) {
        return items.stream().filter(i -> i.worksWith(stack)).findFirst().orElse(null);
    }

    @Deprecated
    @Override
    public Class<?> environmentFor(ItemStack stack) {
        return environmentProviders.stream().map(provider -> provider.getEnvironment(stack)).filter(e -> e instanceof Class<?>).findFirst().orElse(null);
    }

    @Override
    public Set<Class<?>> environmentsFor(ItemStack stack) {
        return environmentProviders.stream().map(e -> e.getEnvironment(stack)).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Storage<ItemVariant> itemHandlerFor(ItemStack stack, PlayerEntity player) {
        // TODO: Figure out how to get a Storage<ItemVariant>
        /*inventoryProviders.stream().filter(provider -> provider.worksWith(stack, player))
                .map()*/
        throw new NotImplementedException("");
    }

    @Override
    public Collection<DriverItem> itemDrivers() {
        return items;
    }
}
