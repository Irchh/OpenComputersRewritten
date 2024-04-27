package no.pepega.oc.api.driver;


import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import no.pepega.oc.api.network.ManagedEnvironment;

/**
 * Interface for side-aware block component drivers.
 * <br>
 * This driver type is used for components that are blocks, i.e. that can be
 * placed in the world, but cannot be modified to or don't want to have their
 * {@link net.minecraft.block.entity.BlockEntity} implement one of the interfaces
 * for environments ({@link no.pepega.oc.api.network.Environment} or
 * {@link no.pepega.oc.api.network.SidedEnvironment}).
 * <br>
 * A block driver is used by <tt>Adapter</tt> blocks to check its neighbors and
 * whether those neighbors should be treated as components or not. If a driver
 * is present, it will be used to create a {@link ManagedEnvironment} that is
 * managed by the adapter.
 * <br>
 * Note that it is possible to write one driver that supports as many different
 * blocks as you wish. I'd recommend writing one per device (type), though, to
 * keep things modular.
 * <br>
 * Note that side-aware block drivers are queried before regular block drivers,
 * because they are more specific.
 */
public interface DriverBlock {
    /**
     * Used to determine the block types this driver handles.
     * <br>
     * This is used to determine which driver to use for a block placed next to
     * an <tt>Adapter</tt> block. Note that the return value should not change
     * over time; if it does, though, an already installed component will not
     * be removed, since this value is only checked when scanning blocks. You
     * can force this by sending a neighbor block change notification.
     * <br>
     * The side is relative to the block, i.e. "south" is the side of the block
     * facing south.
     *
     * @param world the world in which the block to check lives.
     * @param pos   the position coordinate of the block to check.
     * @param side  the side of the block to check.
     * @return <tt>true</tt> if the block is supported; <tt>false</tt> otherwise.
     */
    boolean worksWith(World world, BlockPos pos, Direction side);

    /**
     * Create a new managed environment interfacing the specified block.
     * <br>
     * This is used to connect the component to the component network when it
     * is detected next to an <tt>Adapter</tt>. Components that are not part of
     * the component network probably don't make much sense (can't think of any
     * uses at this time), but you may still opt to not implement this - i.e.
     * it is safe to return <tt>null</tt> here.
     * <br>
     * This is expected to return a <em>new instance</em> each time it is
     * called. The created instance's life cycle is managed by the
     * <tt>Adapter</tt> block that caused its creation.
     * <br>
     * The side is relative to the block, i.e. "south" is the side of the block
     * facing south.
     *
     * @param world the world containing the block to get the environment for.
     * @param pos   the position coordinate of the block to check.
     * @param side  the side of the block to check.
     * @return the environment for the block at that location.
     */
    ManagedEnvironment createEnvironment(World world, BlockPos pos, Direction side);
}