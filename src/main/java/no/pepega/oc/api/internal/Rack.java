package no.pepega.oc.api.internal;

import no.pepega.oc.api.component.RackMountable;
import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.api.network.SidedEnvironment;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;

/**
 * This interface is implemented by the rack tile entity.
 * <br>
 * It particularly allows {@link RackMountable}s installed in the rack to flag
 * themselves as having changed, so their data gets resent to clients.
 * <br>
 * Server racks <em>do not</em> serve as environment for the computer nodes of
 * servers. That's what the {@link no.pepega.oc.api.internal.Server}s are for,
 * which are mountables that can be placed in the rack.
 * <br>
 * Another purpose is to allow identifying tile entities as racks via the API,
 * i.e. without having to link against internal classes. This also means that
 * <em>you should not implement this</em>.
 */
public interface Rack extends SidedEnvironment, EnvironmentHost, Rotatable, Inventory {
    /**
     * Determine the index of the specified mountable.
     *
     * @param mountable the mountable in this rack to get the index of.
     * @return the index in the rack, or <tt>-1</tt> if it's not in the rack.
     */
    int indexOfMountable(RackMountable mountable);

    /**
     * The mountable in the specified slot.
     * <br>
     * This can be <tt>null</tt>, for example when there is no mountable installed
     * in that slot.
     *
     * @param slot the slot in which to get the mountable.
     * @return the mountable currently hosted in the specified slot.
     */
    RackMountable getMountable(int slot);

    /**
     * Get the last data state provided by the mountable in the specified slot.
     * <br>
     * This is also available on the client. This may be <tt>null</tt>.
     *
     * @param slot the slot of the mountable to get the data for.
     * @return the data of the mountable in that slot, or <tt>null</tt>.
     */
    NbtCompound getMountableData(int slot);

    /**
     * Mark the mountable in the specified slot as changed.
     * <br>
     * This will cause the mountable's {@link RackMountable#getData()} method
     * to be called in the next tick and the updated data to be sent to the
     * clients, where it can be used for state based rendering of the mountable
     * for example.
     *
     * @param slot the slot of the mountable to queue for updating.
     */
    void markChanged(int slot);
}
