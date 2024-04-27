package no.pepega.oc;

import no.pepega.oc.api.internal.TextBuffer;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// TODO: config file
public class OCSettings {
    public static final String namespace = "oc:";
    public static final String resourceDomain = OpenComputersRewritten.identifier;
    public static final String scriptPath = "/assets/" + resourceDomain + "/lua/";

    // computer
    public static final int threads = 4;
    public static final double timeout = 5.0;
    public static final double startupDelay = 0.25;
    public static final int eepromSize = 4096;
    public static final int eepromDataSize = 256;
    public static final double[] callBudgets = new double[] {0.5, 1.0, 1.5};
    public static final int[] cpuComponentSupport = new int[] {8, 12, 16, 1024};

    // computer.lua
    public static final int maxTotalRam = 67108864;
    public static final int[] ramSizes = new int[] {192, 256, 384, 512, 768, 1024};

    // power.cost
    public static final double eepromWriteCost = 50;

    //
    public static final int[] CPUComponentByTier = new int[] {8, 12, 16, 1024};

    public static final List<Map.Entry<Integer, Integer>> screenResolutionsByTier = Arrays.asList(new AbstractMap.SimpleEntry<>(50, 16), new AbstractMap.SimpleEntry<>(80, 25), new AbstractMap.SimpleEntry<>(160, 50));
    public static final TextBuffer.ColorDepth[] screenDepthsByTier = new TextBuffer.ColorDepth[] {TextBuffer.ColorDepth.OneBit, TextBuffer.ColorDepth.FourBit, TextBuffer.ColorDepth.EightBit};

    public static final class Constants {
        public static final class DeviceInfo {
            public static final String DefaultVendor = "MightyPirates GmbH & Co. KG";
            public static final String Scummtech = "Scummtech, Inc.";
        }

        public static final class ItemName {
            public static final String Floppy = "floppy";
            public static final String LuaBios = "luabios";
        }
    }
}
