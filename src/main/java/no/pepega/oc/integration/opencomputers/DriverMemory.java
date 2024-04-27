package no.pepega.oc.integration.opencomputers;

import net.minecraft.item.ItemStack;
import no.pepega.oc.OCSettings;
import no.pepega.oc.api.Items;
import no.pepega.oc.api.driver.item.CallBudget;
import no.pepega.oc.api.driver.item.SlotType;
import no.pepega.oc.api.network.EnvironmentHost;
import no.pepega.oc.api.network.ManagedEnvironment;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.item.Memory;

public class DriverMemory extends Item implements no.pepega.oc.api.driver.item.Memory, CallBudget {
    @Override
    public double amount(ItemStack stack) {
        if (stack.getItem() instanceof Memory memory) {
            return OCSettings.ramSizes[Math.min(Math.max(memory.tier(), 0), OCSettings.ramSizes.length - 1)];
        }
        return 0.0;
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        return isOneOf(stack,
                Items.get(no.pepega.oc.common.init.Items.memory0.registryName()),
                Items.get(no.pepega.oc.common.init.Items.memory1.registryName()),
                Items.get(no.pepega.oc.common.init.Items.memory2.registryName()),
                Items.get(no.pepega.oc.common.init.Items.memory3.registryName()),
                Items.get(no.pepega.oc.common.init.Items.memory4.registryName()),
                Items.get(no.pepega.oc.common.init.Items.memory5.registryName()));
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new no.pepega.oc.common.component.Memory(tier(stack));
    }

    @Override
    public SlotType slot(ItemStack stack) {
        return SlotType.Memory;
    }

    @Override
    public int tier(ItemStack stack) {
        if (stack.getItem() instanceof Memory memory) {
            return memory.tier();
        }
        return super.tier(stack);
    }

    @Override
    public double getCallBudget(ItemStack stack) {
        return OCSettings.callBudgets[Math.min(Math.max(tier(stack), Tier.One), Tier.Three)];
    }
}
