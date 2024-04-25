package no.pepega.oc.api;

import no.pepega.oc.api.machine.Architecture;
import no.pepega.oc.common.registry.OCRegistry;

public final class Machine {
    /**
     * Get the name of the specified architecture.
     *
     * @param architecture the architecture to get the name for.
     * @return the name of the specified architecture.
     */
    public static String getArchitectureName(Class<? extends Architecture> architecture) {
        return OCRegistry.getArchitectureName(architecture);
    }
}
