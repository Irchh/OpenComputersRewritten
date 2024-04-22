package no.pepega.oc.api.internal;

public interface TextBuffer {
    /**
     * Used when setting a buffer's maximum color depth.
     */
    enum ColorDepth {
        /**
         * Monochrome color, black and white.
         */
        OneBit,

        /**
         * 16 color palette, defaults to Minecraft colors.
         */
        FourBit,

        /**
         * 240 colors, 16 color palette, defaults to grayscale.
         */
        EightBit
    }
}
