package no.pepega.oc.api.driver.item;

import java.util.Arrays;
import java.util.List;

public enum SlotType {
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

    public static final List<SlotType> All = Arrays.asList(Card, ComponentBus, Container, CPU, EEPROM, Floppy, HDD, Memory, RackMountable, Tablet, Tool, Upgrade);

    public final String label;

    SlotType(String label) {
        this.label = label;
    }
}
