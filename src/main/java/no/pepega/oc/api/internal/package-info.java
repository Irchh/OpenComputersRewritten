/**
 * This package provides interfaces that are implemented by OC internal
 * classes so that they can be checked for and used by type checking and
 * casting to these interfaces.
 * <br>
 * For example, to determine whether a tile entity is a robot, you can
 * do an <tt>instanceof</tt> with the {@link no.pepega.oc.api.internal.Robot}
 * interface - and cast to it if you wish to access some of the provided
 * functionality.
 * <br>
 * The other main use-case is in {@link no.pepega.oc.api.driver.item.HostAware}
 * drivers, where these interfaces can be used to check if the item can be
 * used inside the specified environment (where the environment class may
 * be assignable to one of the interfaces in this package).
 */
package no.pepega.oc.api.internal;

import no.pepega.oc.api.API;