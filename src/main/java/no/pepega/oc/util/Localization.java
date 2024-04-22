package no.pepega.oc.util;

import net.minecraft.util.Language;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Localization {
    public static final String namespace = "oc:";

    private static String resolveKey(String key) {
        if (canLocalize(namespace + key))
            return namespace + key;
        else
            return key;
    }

    public static boolean canLocalize(String key) {
        return Language.getInstance().hasTranslation(key);
    }

    public static String localizeImmediately(String formatKey, Object... values) {
        String k = resolveKey(formatKey);
        Language lm = Language.getInstance();
        if (!lm.hasTranslation(k)) return k;
        return Arrays.stream(String.format(lm.get(k), values).split("\\r?\\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"));
    }
}
