package no.pepega.oc.common.item;

import no.pepega.oc.api.component.ComponentItem;
import no.pepega.oc.api.component.ComponentType;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.item.util.ExtendedItem;

public class EEPROM extends ExtendedItem implements ComponentItem {
    public EEPROM(Settings settings) {
        super(settings);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.EEPROM;
    }

    @Override
    public int tier() {
        return Tier.None;
    }
}
