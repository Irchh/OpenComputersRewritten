package no.pepega.oc.common.item;

import no.pepega.oc.common.item.util.ExtendedItem;
import no.pepega.oc.common.item.util.Tier;

public class Memory extends ExtendedItem implements Tier {
    private final int tier;

    public Memory(Settings settings, int tier) {
        super(settings);
        this.tier = tier;
    }

    @Override
    public String registryName() {
        return super.registryName() + tier;
    }

    @Override
    public int tier() {
        return this.tier;
    }
}
