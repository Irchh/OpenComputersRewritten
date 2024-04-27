package no.pepega.oc.api.detail;


import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import no.pepega.oc.api.driver.*;
import no.pepega.oc.api.network.EnvironmentHost;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

public interface DriverAPI {
    /**
     * Registers a new side-aware block driver.
     * <br>
     * Whenever the neighboring blocks of an Adapter block change, it checks if
     * there exists a driver for the changed block, and if it is configured to
     * interface that block type connects it to the component network.
     * <br>
     * This must be called in the init phase, <em>not</em> the pre- or post-init
     * phases.
     *
     * @param driver the driver to register.
     */
    void add(DriverBlock driver);

    /**
     * Registers a new driver for an item component.
     * <br>
     * Item components can inserted into a computers component slots. They have
     * to specify their type, to determine into which slots they can fit.
     * <br>
     * This must be called in the init phase, <em>not</em> the pre- or post-init
     * phases.
     *
     * @param driver the driver for an item component.
     */
    void add(DriverItem driver);

    /**
     * Registers a new type converter.
     * <br>
     * Type converters are used to automatically convert values returned from
     * callbacks to a "simple" format that can be pushed to any architecture.
     * <br>
     * This must be called in the init phase, <em>not</em> the pre- or post-init
     * phases.
     *
     * @param converter the converter to register.
     */
    void add(Converter converter);

    /**
     * Register a new environment provider.
     * <br>
     * Environment providers are used for mapping item stacks to the type of
     * environment that will be created by the stack, either by it being
     * placed in the world and acting as a block component, or by being
     * placed in an component inventory and created by the item's driver.
     *
     * @param provider the provider to register.
     */
    void add(EnvironmentProvider provider);

    /**
     * Register a new inventory provider.
     * <br>
     * Inventory providers are used for accessing item inventories using
     * the inventory controller upgrade, for example.
     *
     * @param provider the provider to register.
     */
    void add(InventoryProvider provider);

    /**
     * Looks up a driver for the block at the specified position in the
     * specified world.
     * <br>
     * Note that several drivers for a single block can exist. Because of this
     * block drivers are always encapsulated in a 'compound' driver, which is
     * what will be returned here. In other words, you should will <em>not</em>
     * get actual instances of drivers registered via {@link #add(DriverBlock)}.
     *
     * @param world the world containing the block.
     * @param pos   the position of the block.
     * @param side  the side of the block.
     * @return a driver for the block, or <tt>null</tt> if there is none.
     */
    @Nullable
    DriverBlock driverFor(World world, BlockPos pos, Direction side);

    /**
     * Looks up a driver for the specified item stack.
     * <br>
     * Note that unlike for blocks, there can always only be one item driver
     * per item. If there are multiple ones, the first one that was registered
     * will be used.
     *
     * @param stack the item stack to get a driver for.
     * @param host  the type that will host the environment created by returned driver.
     * @return a driver for the item, or <tt>null</tt> if there is none.
     */
    @Nullable
    DriverItem driverFor(ItemStack stack, Class<? extends EnvironmentHost> host);

    /**
     * Looks up a driver for the specified item stack.
     * <br>
     * Note that unlike for blocks, there can always only be one item driver
     * per item. If there are multiple ones, the first one that was registered
     * will be used.
     * <br>
     * This is a context-agnostic variant used mostly for "house-keeping"
     * stuff, such as querying slot types and tier.
     *
     * @param stack the item stack to get a driver for.
     * @return a driver for the item, or <tt>null</tt> if there is none.
     */
    @Nullable
    DriverItem driverFor(ItemStack stack);

    /**
     * Looks up the environment associated with the specified item stack.
     * <br>
     * This will use the registered {@link EnvironmentProvider}s to find
     * an environment type for the specified item stack. If none can be
     * found, returns <tt>null</tt>.
     *
     * @param stack the item stack to get the environment type for.
     * @return the type of environment associated with the stack, or <tt>null</tt>.
     * @deprecated Use {@link #environmentsFor(ItemStack)} instead.
     */
    @Deprecated
    Class<?> environmentFor(ItemStack stack);

    /**
     * Looks up the environments associated with the specified item stack.
     * <br>
     * This will use the registered {@link EnvironmentProvider}s to find
     * environment types for the specified item stack. If none can be
     * found, returns an empty Set.
     *
     * @param stack the item stack to get the environment type for.
     * @return the type of environment associated with the stack, or an empty Set.
     */
    Set<Class<?>> environmentsFor(ItemStack stack);

    /**
     * Get an IItemHandler implementation providing access to an item inventory.
     * <br>
     * This will use the registered {@link InventoryProvider}s to find an
     * IItemHandler implementation providing access to the specified stack.
     * If none can be found, returns <tt>null</tt>.
     * <br>
     * Note that the specified <tt>player</tt> may be null, but will usually
     * be the <em>fake player</em> of the agent making use of this API.
     *
     * @param stack  the item stack to get the inventory access for.
     * @param player the player holding the item. May be <tt>null</tt>.
     * @return the IItemHandler implementation interfacing the stack, or <tt>null</tt>.
     */
    Storage<ItemVariant> itemHandlerFor(ItemStack stack, PlayerEntity player);

    /**
     * Get a list of all registered item drivers.
     * <br>
     * This is intended to allow checking for particular drivers using more
     * customized logic.
     * <br>
     * The returned collection is read-only.
     *
     * @return the list of all registered item drivers.
     */
    Collection<DriverItem> itemDrivers();
}
