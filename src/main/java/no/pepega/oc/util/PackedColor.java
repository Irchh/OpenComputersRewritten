package no.pepega.oc.util;

import no.pepega.oc.api.internal.TextBuffer;

public class PackedColor {
    public static class Depth {
        public static int bits(TextBuffer.ColorDepth depth) {
            return switch (depth) {
                case OneBit -> 1;
                case FourBit -> 4;
                case EightBit -> 8;
            };
        }
    }
}
