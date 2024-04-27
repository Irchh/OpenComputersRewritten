package no.pepega.oc.api.internal;

import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.api.machine.MachineHost;
import net.minecraft.entity.player.PlayerEntity;

/**
 * This interface is implemented as a marker by tablets.
 * <br>
 * This is implemented by the class containing tablets' logic implementation,
 * which is <em>not the tablet item</em>! The tablet class serves as its
 * computer components' environment. That means you can use this to check for
 * tablets by using
 * <pre>
 *     if (node.host() instanceof Tablet) {
 * </pre>
 * <br>
 * This can also be used by {@link no.pepega.oc.api.driver.item.HostAware} item
 * drivers to check if the provided environment class is a tablet by checking
 * for assignability, which allows for items that make no sense in tablets to
 * deny being placed into them in the assembler, for example.
 * <br>
 * The only purpose is to allow identifying tile entities as tablets
 * via the API, i.e. without having to link against internal classes. This
 * also means that <em>you should not implement this</em>.
 */
public interface Tablet extends EnvironmentHost, MachineHost, Rotatable {
    /**
     * Returns the player last holding the tablet.
     * <br>
     * Note that this value may change over the lifetime of a tablet instance.
     * The player may also already have dropped the tablet - this value will
     * <em>not</em> be set to <tt>null</tt> in that case!
     *
     * @return the player last holding the tablet.
     */
    PlayerEntity player();
}
