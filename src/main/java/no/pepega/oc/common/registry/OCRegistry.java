package no.pepega.oc.common.registry;


import no.pepega.oc.api.machine.Architecture;

import java.util.List;

public class OCRegistry {
    private static List<Architecture> architectureList;

    public static void register(Architecture architecture) {
        if (!architectureList.contains(architecture)) {
            architectureList.add(architecture);
        }
    }

    public static List<Architecture> getArchitectures() {
        return architectureList;
    }

    public static String getArchitectureName(Class<? extends Architecture> architecture) {
        if (architecture == null) {
            return "None";
        }
        if (architecture.getAnnotation(Architecture.Name.class) instanceof Architecture.Name name) {
            return name.value();
        }
        return architecture.getSimpleName();
    }
}
