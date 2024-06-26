package no.pepega.oc.api.util;

import java.util.EnumSet;

/**
 * Implemented on machines that have an "working" state.
 * <br>
 * This is similar to BuildCraft's <tt>IHasWork</tt> interface.
 * <br>
 * This can also be implemented on {@link no.pepega.oc.api.component.RackMountable}s
 * to indicate a working state, which is used when checking for redstone cards
 * in them, for example (only active mountables will be used).
 */
public interface StateAware {
    /**
     * Get the current work state (usually a <tt>TileEntity</tt>.
     * <br>
     * An empty set indicates that no work can be performed.
     *
     * @return the current state.
     */
    EnumSet<State> getCurrentState();

    /**
     * Possible work states.
     */
    enum State {
        None,

        /**
         * Indicates that some work can be performed / energy can be consumed,
         * but that the current state is being idle.
         */
        CanWork,

        /**
         * Indicates that some work is currently being performed / some energy
         * is currently being consumed.
         */
        IsWorking
    }
}

