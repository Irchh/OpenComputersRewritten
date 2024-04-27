package no.pepega.oc.api.network;

/**
 * This type is used to deliver messages sent in a component network.
 * <br>
 * We use an extra class to deliver messages to nodes to make the cancel logic
 * more clear (returning a boolean can get annoying very fast).
 */
public interface Message {
    /**
     * The node that sent the message.
     *
     * @return the source node.
     */
    Node source();

    /**
     * The name of this message.
     *
     * @return the name of the message.
     */
    String name();

    /**
     * The values passed along in the message.
     *
     * @return the message data.
     */
    Object[] data();

    /**
     * Stop further propagation of a broadcast message.
     * <br>
     * This can be used to stop further distributing messages when either
     * serving a message to a specific address and there are multiple nodes
     * with that address, or when serving a broadcast message.
     */
    void cancel();
}