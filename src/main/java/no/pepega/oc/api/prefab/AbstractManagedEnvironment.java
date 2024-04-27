package no.pepega.oc.api.prefab;

import net.minecraft.nbt.NbtCompound;
import no.pepega.oc.api.network.ManagedEnvironment;
import no.pepega.oc.api.network.Message;
import no.pepega.oc.api.network.Node;

/**
 * Simple base implementation of the <tt>ManagedEnvironment</tt> interface, so
 * unused methods don't clutter the implementing class.
 */
public abstract class AbstractManagedEnvironment implements ManagedEnvironment {
    public static final String NODE_TAG = "node";

    // Should be initialized using setNode(api.Network.newNode()). See TileEntityEnvironment.
    private Node _node;

    @Override
    public Node node() {
        return _node;
    }

    protected void setNode(Node value) {
        _node = value;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void update() {
    }

    @Override
    public void onConnect(final Node node) {
    }

    @Override
    public void onDisconnect(final Node node) {
    }

    @Override
    public void onMessage(final Message message) {
    }

    @Override
    public void loadData(final NbtCompound nbt) {
        if (node() != null) {
            node().loadData(nbt.getCompound(NODE_TAG));
        }
    }

    @Override
    public void saveData(final NbtCompound nbt) {
        if (node() != null) {
            // Force joining a network when saving and we're not in one yet, so that
            // the address is embedded in the saved data that gets sent to the client,
            // so that that address can be used to associate components on server and
            // client (for example keyboard and screen/text buffer).
            if (node().address() == null) {
                no.pepega.oc.api.Network.joinNewNetwork(node());

                final NbtCompound nodeTag = new NbtCompound();
                node().saveData(nodeTag);
                nbt.put(NODE_TAG, nodeTag);

                node().remove();
            } else {
                final NbtCompound nodeTag = new NbtCompound();
                node().saveData(nodeTag);
                nbt.put(NODE_TAG, nodeTag);
            }
        }
    }
}