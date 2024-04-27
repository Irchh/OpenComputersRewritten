package no.pepega.oc.api.network;

import net.fabricmc.api.EnvType;
import net.minecraft.util.math.Direction;

/**
 * This interface is like {@link net.minecraft.inventory.SidedInventory} is to
 * {@link net.minecraft.inventory.Inventory}, it allows an environment to
 * specify different node access for its different sides.
 * <br>
 * This interface is intended to be used on tile entities that are environments.
 * It is used to determine which neighbors a tile entity can connect to when
 * calling {@link no.pepega.oc.api.Network#joinOrCreateNetwork(TileEntity)}. It is
 * used by the keyboard to only interface with the side on which it is attached,
 * as well as the switch to offer a different node for each side.
 */
public interface SidedEnvironment {
    /**
     * The node this environment uses for the specified side.
     * <br>
     * This is the side aware version of the normal {@link no.pepega.oc.api.network.Environment#node}
     * method.
     * <br>
     * The provided side is relative to the environment, i.e. when the tile
     * entity hosting the environment sits at (0, 0, 0) and is asked for its
     * southern node (positive Z axis) it has to return the node for the face
     * between it and the block at (0, 0, 1).
     *
     * @param side the side to get the node for.
     * @return the node for the specified side.
     * @see no.pepega.oc.api.network.Environment#node
     */
    Node sidedNode(Direction side);

    /**
     * Whether the environment provides a node to connect to on the specified
     * side.
     * <br>
     * For each side the environment returns <tt>false</tt> here, it should
     * return <tt>null</tt> from {@link #sidedNode}, and for each side it
     * returns <tt>true</tt> for it should return a node.
     * <br>
     * This is intended for the client side, i.e. rendering related things,
     * since nodes are not created on the client side.
     * <br>
     * The side is relative to the environment, same as for <tt>sidedNode</tt>.
     *
     * @param side the side to check for.
     * @return whether the environment provides a node for the specified side.
     */
    @net.fabricmc.api.Environment(EnvType.CLIENT)
    boolean canConnect(Direction side);
}
