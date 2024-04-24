package no.pepega.oc.common.item;

import no.pepega.oc.api.component.ComponentItem;
import no.pepega.oc.common.SlotType;
import no.pepega.oc.common.item.util.ExtendedItem;

public class Memory extends ExtendedItem implements ComponentItem {
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
    public String componentType() {
        return SlotType.Memory;
    }

    @Override
    public int tier() {
        return this.tier/2;
    }
}
