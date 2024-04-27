package no.pepega.oc.api.internal;

import no.pepega.oc.api.Persistable;
import no.pepega.oc.api.network.Environment;
import net.minecraft.entity.player.PlayerEntity;

/**
 * This interface is implemented by the keyboard component, to allow more
 * flexible use of it.
 * <br>
 * You can obtain an instance of the keyboard component via the item driver
 * of the keyboard block, for example:
 * <pre>
 *     final ItemStack stack = no.pepega.oc.api.Items.get("keyboard").createItemStack(1);
 *     final Keyboard keyboard = (Keyboard) no.pepega.oc.api.Driver.driverFor(stack).createEnvironment(stack, this);
 * </pre>
 */
public interface Keyboard extends Environment, Persistable {
    /**
     * Sets a custom usability override.
     * <br>
     * Instead of the default check, which is based on the component's owner's
     * position, the specified callback will be queried for usability checks
     * instead.
     * <br>
     * Pass <tt>null</tt> here to unset a previously set override.
     *
     * @param callback the usability checker to use.
     */
    void setUsableOverride(UsabilityChecker callback);

    /**
     * Contract interface that has to implemented for usability check overrides.
     *
     * @see #setUsableOverride(Keyboard.UsabilityChecker)
     */
    interface UsabilityChecker {
        /**
         * Whether the specified keyboard is usable by the specified player.
         *
         * @param keyboard the keyboard to check for.
         * @param player   the player to check for.
         * @return whether the keyboard is usable by the player.
         */
        boolean isUsableByPlayer(Keyboard keyboard, PlayerEntity player);
    }
}
