package no.pepega.oc.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import no.pepega.oc.client.KeyBindings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Tooltip {
    public static String namespace = "oc:";

    private static int maxWidth = 220;
    private static TextRenderer font = MinecraftClient.getInstance().textRenderer;

    public static Style DefaultStyle = Style.EMPTY.withColor(Formatting.GRAY);

    public static List<String> get(String name, Object ...args) {
        if (!Localization.canLocalize(Localization.namespace + "tooltip." + name))
            return new ArrayList<>();

        String tooltip;
        try {
            tooltip = Localization.localizeImmediately("tooltip." + name, args);
        } catch (Exception e) {
            System.err.println("Error localizing string, create an issue at https://github.com/Irchh/OpenComputersRewritten");
            System.err.println("Tried localizing: " + e);
            System.err.println("Error: " + e);
            tooltip = "tooltip." + name;
        }
        if (font == null) return Arrays.asList(tooltip.split("\\r?\\n"));
        boolean isSubTooltip = name.contains(".");
        boolean shouldShorten = (isSubTooltip || font.getWidth(tooltip) > maxWidth) && !KeyBindings.showExtendedTooltips();
        if (shouldShorten) {
            if (isSubTooltip) return new ArrayList<>();
            else return Collections.singletonList(Localization.localizeImmediately("tooltip.toolong", KeyBindings.getKeyBindingName(KeyBindings.extendedTooltip)));
        } else {
            return Arrays.stream(tooltip.split("\\r?\\n"))
                    .flatMap(line -> wrap(font, line, maxWidth).stream().map(wrappedLine -> wrappedLine.trim() + " "))
                    .collect(Collectors.toList());
        }
    }

    public static List<String> extended(String name, Object ...args) {
        if (KeyBindings.showExtendedTooltips()) {
            return Arrays.stream(String.format(Localization.localizeImmediately("tooltip." + name), args)
                            .split("\\r?\\n"))
                    .flatMap(line -> wrap(font, line, maxWidth).stream().map(wrappedLine -> wrappedLine.trim() + " "))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private static List<String> wrap(TextRenderer font, String tooltip, int width) {
        List<String> list = new ArrayList<>();
        list.add("");

        Arrays.stream(tooltip.split(" "))
                .forEach(word -> {
                    // Don't split names like "Graphics Card" into two lines.
                    if (!word.equalsIgnoreCase("card") && font.getWidth(list.getLast() + " " + word) > width) {
                        list.add("");
                    }
                    String last = list.removeLast() + " " + word;
                    list.add(last);
                });
        return list;
    }
}
