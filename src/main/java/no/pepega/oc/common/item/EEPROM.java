package no.pepega.oc.common.item;

import no.pepega.oc.api.component.ComponentItem;
import no.pepega.oc.common.SlotType;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.item.util.ExtendedItem;

public class EEPROM extends ExtendedItem implements ComponentItem {
    public EEPROM(Settings settings) {
        super(settings);
    }

    @Override
    public String componentType() {
        return SlotType.EEPROM;
    }

    @Override
    public int tier() {
        return Tier.None;
    }
}
