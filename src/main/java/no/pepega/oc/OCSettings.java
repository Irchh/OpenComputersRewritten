package no.pepega.oc;

import no.pepega.oc.api.internal.TextBuffer;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OCSettings {
    public static final int[] CPUComponentByTier = new int[] {8, 12, 16, 1024};

    public static final List<Map.Entry<Integer, Integer>> screenResolutionsByTier = Arrays.asList(new AbstractMap.SimpleEntry<>(50, 16), new AbstractMap.SimpleEntry<>(80, 25), new AbstractMap.SimpleEntry<>(160, 50));
    public static final TextBuffer.ColorDepth[] screenDepthsByTier = new TextBuffer.ColorDepth[] {TextBuffer.ColorDepth.OneBit, TextBuffer.ColorDepth.FourBit, TextBuffer.ColorDepth.EightBit};
}
