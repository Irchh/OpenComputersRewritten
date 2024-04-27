package no.pepega.oc.integration.opencomputers;

import net.minecraft.item.ItemStack;
import no.pepega.oc.api.Items;
import no.pepega.oc.api.driver.EnvironmentProvider;
import no.pepega.oc.api.driver.item.SlotType;
import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.api.network.ManagedEnvironment;
import no.pepega.oc.common.component.EEPROM;

public class DriverEEPROM extends Item {
    @Override
    public boolean worksWith(ItemStack stack) {
        return isOneOf(stack, Items.get(no.pepega.oc.common.init.Items.eeprom.registryName()));
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        if (host.world() != null && host.world().isClient())
            return null;
        return new EEPROM();
    }

    @Override
    public SlotType slot(ItemStack stack) {
        return SlotType.EEPROM;
    }

    public static class Provider implements EnvironmentProvider {
        @Override
        public Class<?> getEnvironment(ItemStack stack) {
            if (new DriverEEPROM().worksWith(stack))
                return EEPROM.class;
            else
                return null;
        }
    }
}
