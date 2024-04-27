package no.pepega.oc.common.item.util;

import no.pepega.oc.util.PackedColor;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static no.pepega.oc.OCSettings.screenDepthsByTier;
import static no.pepega.oc.OCSettings.screenResolutionsByTier;

public interface GPULike {
    int gpuTier();

    default List<Object> gpuTooltipData() {
        int w = screenResolutionsByTier.get(gpuTier()).getKey();
        int h = screenResolutionsByTier.get(gpuTier()).getValue();
        int depth = PackedColor.Depth.bits(screenDepthsByTier[gpuTier()]);

        switch (Math.max(0, Math.min(gpuTier(), 2))) {
            case 0: return Arrays.asList(w, h, depth, "1/1/4/2/2");
            case 1: return Arrays.asList(w, h, depth, "2/4/8/4/4");
            case 2: return Arrays.asList(w, h, depth, "4/8/16/8/8");
        }
        throw new RuntimeException("Unreachable code");
    }
}
