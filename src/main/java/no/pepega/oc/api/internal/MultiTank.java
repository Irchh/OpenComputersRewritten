package no.pepega.oc.api.internal;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

/**
 * Implemented by objects with multiple internal tanks.
 * <br>
 * This is specifically for containers where the side does not matter when
 * accessing the internal tanks, only the index of the tank; unlike with the
 * {@link net.fabricmc.fabric.api.transfer.v1.storage.Storage<FluidVariant>} interface.
 */
public interface MultiTank {
    /**
     * The number of tanks currently installed.
     */
    int tankCount();

    /**
     * Get the installed fluid tank with the specified index.
     *
     * @param index the index of the tank to get.
     * @return the tank with the specified index.
     */
    Storage<FluidVariant> getFluidTank(int index);
}
