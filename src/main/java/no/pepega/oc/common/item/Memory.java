package no.pepega.oc.common.item;

import no.pepega.oc.api.internal.Tiered;
import no.pepega.oc.common.item.util.ExtendedItem;

public class Memory extends ExtendedItem implements Tiered {
    private final int tier;

    public Memory(net.minecraft.item.Item.Settings settings, int tier) {
        super(settings);
        this.tier = tier;
    }

    @Override
    public String registryName() {
        return super.registryName() + tier;
    }
    @Override
    public int tier() {
        return this.tier/2;
    }
}
