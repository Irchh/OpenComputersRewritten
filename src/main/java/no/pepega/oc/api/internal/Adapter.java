package no.pepega.oc.api.internal;

import net.minecraft.inventory.Inventory;
import no.pepega.oc.api.network.Environment;

/**
 * This interface is implemented as a marker by adapters.
 * <br>
 * This is implemented by adapter tile entities, which also serve as its
 * components' environment. That means you can use this to check for
 * adapters by using either:
 * <pre>
 *     if (tileEntity instanceof Adapter) {
 * </pre>
 * or
 * <pre>
 *     if (node.host() instanceof Adapter) {
 * </pre>
 * <br>
 * This can also be used by {@link no.pepega.oc.api.driver.item.HostAware} item
 * drivers to check if the provided environment class is an adapter by checking
 * for assignability, which allows for items that make no sense in adapters to
 * deny being placed into them, for example.
 * <br>
 * The only purpose is to allow identifying tile entities as adapters
 * via the API, i.e. without having to link against internal classes. This
 * also means that <em>you should not implement this</em>.
 */
public interface Adapter extends Environment, Inventory {
}
