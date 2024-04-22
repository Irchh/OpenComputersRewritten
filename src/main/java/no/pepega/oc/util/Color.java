package no.pepega.oc.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Color {
    public static final Map<DyeColor, Integer> rgbValues = new HashMap<>() {{
        put(DyeColor.BLACK, 0x444444);
        put(DyeColor.RED, 0xB3312C);
        put(DyeColor.GREEN, 0x339911);
        put(DyeColor.BROWN, 0x51301A);
        put(DyeColor.BLUE, 0x6666FF);
        put(DyeColor.PURPLE, 0x7B2FBE);
        put(DyeColor.CYAN, 0x66FFFF);
        put(DyeColor.LIGHT_GRAY, 0xABABAB);
        put(DyeColor.GRAY, 0x666666);
        put(DyeColor.PINK, 0xD88198);
        put(DyeColor.LIME, 0x66FF66);
        put(DyeColor.YELLOW, 0xFFFF66);
        put(DyeColor.LIGHT_BLUE, 0xAAAAFF);
        put(DyeColor.MAGENTA, 0xC354CD);
        put(DyeColor.ORANGE, 0xEB8844);
        put(DyeColor.WHITE, 0xF0F0F0);
    }};

    public static List<DyeColor> byTier = Arrays.asList(DyeColor.LIGHT_GRAY, DyeColor.YELLOW, DyeColor.CYAN, DyeColor.MAGENTA);
}
