package no.pepega.oc.common;

import no.pepega.oc.api.driver.item.SlotTypeAPI;

import java.util.Arrays;
import java.util.List;

public class SlotType {
    public static final String None = SlotTypeAPI.None;
    public static final String Any = SlotTypeAPI.Any;
    public static final String Filtered = "filtered";
    public static final String Card = SlotTypeAPI.Card;
    public static final String ComponentBus = SlotTypeAPI.ComponentBus;
    public static final String Container = SlotTypeAPI.Container;
    public static final String CPU = SlotTypeAPI.CPU;
    public static final String EEPROM = "eeprom";
    public static final String Floppy = SlotTypeAPI.Floppy;
    public static final String HDD = SlotTypeAPI.HDD;
    public static final String Memory = SlotTypeAPI.Memory;
    public static final String RackMountable = SlotTypeAPI.RackMountable;
    public static final String Tablet = SlotTypeAPI.Tablet;
    public static final String Tool = "tool";
    public static final String Upgrade = SlotTypeAPI.Upgrade;
    public static final List<String> All = Arrays.asList(Card, ComponentBus, Container, CPU, EEPROM, Floppy, HDD, Memory, RackMountable, Tablet, Tool, Upgrade);
}
