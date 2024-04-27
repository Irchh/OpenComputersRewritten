package no.pepega.oc.api.internal;

import net.minecraft.inventory.Inventory;
import no.pepega.oc.api.machine.MachineHost;
import no.pepega.oc.api.network.Environment;
import no.pepega.oc.api.network.EnvironmentHost;

/**
 * This interface is implemented as a marker by computer cases.
 * <br>
 * This is implemented by computer case tile entities. That means you can
 * use this to check for computer cases by using:
 * <pre>
 *     if (tileEntity instanceof Case) {
 * </pre>
 * <br>
 * The only purpose is to allow identifying tile entities as computer cases
 * via the API, i.e. without having to link against internal classes. This
 * also means that <em>you should not implement this</em>.
 */
public interface Case extends Environment, EnvironmentHost, MachineHost, Colored, Rotatable, Tiered, Inventory {
}
