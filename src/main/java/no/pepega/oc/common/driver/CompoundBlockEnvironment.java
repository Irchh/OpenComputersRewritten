package no.pepega.oc.common.driver;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.api.Network;
import no.pepega.oc.api.network.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CompoundBlockEnvironment implements ManagedEnvironment {
    private final List<Pair<String, ManagedEnvironment>> environments;

    private final Component node;
    private final List<ManagedEnvironment> updatingEnvironments;

    // Method to get the maximum reachability from environments
    private Visibility getMaxReachability() {
        List<Visibility> reachabilities = new ArrayList<>();
        for (Pair<String, ManagedEnvironment> environment: environments) {
            if (environment.getRight().node() != null) {
                reachabilities.add(environment.getRight().node().reachability());
            }
        }
        reachabilities.add(Visibility.None);
        return Visibility.values()[reachabilities.stream().mapToInt(Enum::ordinal).max().orElse(0)];
    }

    public CompoundBlockEnvironment(String name, List<Pair<String, ManagedEnvironment>> environments) {
        this.environments = environments;

        // Block drivers with visibility < network usually won't make much sense,
        // but let's play it safe and use the least possible visibility based on
        // the drivers we encapsulate.
        this.node = Network.newNode(this, getMaxReachability())
                .withComponent(name)
                .create();
        updatingEnvironments = environments.stream().map(Pair::getRight).filter(ManagedEnvironment::canUpdate).toList();

        // Force all wrapped components to be neighbor visible, since we as their
        // only neighbor will take care of all component-related interaction.
        for (var environment: environments) {
            if (environment.getRight().node() instanceof Component component) {
                component.setVisibility(Visibility.Neighbors);
            }
        }
    }

    @Override
    public boolean canUpdate() {
        return environments.stream().anyMatch(e -> e.getRight().canUpdate());
    }

    @Override
    public void update() {
        for (var environment: updatingEnvironments) {
            environment.update();
        }
    }

    @Override
    public void onMessage(Message message) {}

    @Override
    public void onConnect(Node node) {
        if (node == this.node) {
            for (var environment: environments) {
                if (environment.getRight().node() != null) {
                    node.connect(environment.getRight().node());
                }
            }
        }
    }

    @Override
    public void onDisconnect(Node node) {
        if (node == this.node) {
            for (var environment: environments) {
                if (environment.getRight().node() != null) {
                    environment.getRight().node().remove();
                }
            }
        }
    }

    private final String TypeHashTag = "typeHash";

    private Long typeHash() {
        var hash = Hashing.sha256().newHasher();
        environments.stream().map(e -> e.getRight().getClass().getName()).sorted().forEach(s -> hash.putString(s, Charset.defaultCharset()));
        return hash.hash().asLong();
    }

    @Override
    public void loadData(NbtCompound nbt) {
        // Ignore existing data if the underlying type is different.
        if (nbt.contains(TypeHashTag) && nbt.getLong(TypeHashTag) != typeHash())
            return;
        node.loadData(nbt);
        for (var e: environments) {
            var driver = e.getLeft();
            var environment = e.getRight();
            if (nbt.contains(driver)) {
                try {
                    environment.loadData(nbt.getCompound(driver));
                } catch (Throwable t) {
                    OpenComputersRewritten.log.warn(String.format("A block component of type '%s' (provided by driver '%s') threw an error while loading.", environment.getClass().getName(), driver), t);
                }
            }
        }
    }

    @Override
    public void saveData(NbtCompound nbt) {
        nbt.putLong(TypeHashTag, typeHash());
        node.saveData(nbt);
        for (var e: environments) {
            var driver = e.getLeft();
            var environment = e.getRight();
            try {
                var t = new NbtCompound();
                environment.saveData(t);
                nbt.put(driver, t);
            } catch (Throwable t) {
                OpenComputersRewritten.log.warn(String.format("A block component of type '%s' (provided by driver '%s') threw an error while saving.", environment.getClass().getName(), driver), t);
            }
        }
    }

    @Override
    public Node node() {
        return this.node;
    }
}
