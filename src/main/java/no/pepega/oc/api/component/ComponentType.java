package no.pepega.oc.api.component;

import java.util.Arrays;
import java.util.List;

public enum ComponentType {
    None("none"),
    Any("any"),

    Filtered("filtered"),
    Card("card"),
    ComponentBus("component_bus"),
    Container("container"),
    CPU("cpu"),
    EEPROM("eeprom"),
    Floppy("floppy"),
    HDD("hdd"),
    Memory("memory"),
    RackMountable("rack_mountable"),
    Tablet("tablet"),
    Tool("tool"),
    Upgrade("upgrade");

    public static final List<ComponentType> All = Arrays.asList(Card, ComponentBus, Container, CPU, EEPROM, Floppy, HDD, Memory, RackMountable, Tablet, Tool, Upgrade);

    public final String label;

    ComponentType(String label) {
        this.label = label;
    }
}
