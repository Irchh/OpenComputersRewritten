/**
 * This API provides interfaces and factory methods for the OpenComputers mod.
 * <br>
 * There are several parts to this API:
 * <dl>
 * <dt>The {@link no.pepega.oc.api.Driver} API</dt>
 * <dd>
 * This API is used to provide glue code to the mod that allows it to interact
 * with foreign objects. You need a driver if you wish to connect some object
 * to the internal {@link no.pepega.oc.api.network.Network}, for example because
 * you wish to interact with other blocks / components of the mod. The most
 * typical scenario for this will be adding a new object that user programs
 * should be able to interact with: a {@link no.pepega.oc.api.network.Component}.
 * <br>
 * Note that for tile entities you implement yourself, you will not have to
 * provide a driver, as long as you implement the necessary interface:
 * {@link no.pepega.oc.api.network.Environment} and call {@link no.pepega.oc.api.Network#joinOrCreateNetwork(net.minecraft.block.entity.BlockEntity)}
 * in the first <tt>tick()</tt> call. For items that should be installed
 * in a computer you will always have to provide a driver.
 * </dd>
 * <dt>The {@link no.pepega.oc.api.FileSystem} API</dt>
 * <dd>
 * This API provides facilities that make it easier to create file systems that
 * can be interacted with from user programs via the file system driver that
 * comes with the mod.
 * </dd>
 * <dt>The {@link no.pepega.oc.api.Network} API</dt>
 * <dd>
 * This API provides interfaces that allow interacting with the internal network
 * and creating nodes, components and power connectors for said network. If you
 * implement <tt>Environment</tt> in your tile entity or provide a
 * {@link no.pepega.oc.api.network.ManagedEnvironment} via a driver you'll want to
 * create a node. This API provides factory methods for creating it.
 * </dd>
 * </dl>
 */
package no.pepega.oc.api;