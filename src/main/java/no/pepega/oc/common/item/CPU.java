package no.pepega.oc.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import no.pepega.oc.api.machine.Architecture;
import no.pepega.oc.common.item.util.CPULike;
import no.pepega.oc.common.item.util.ExtendedItem;
import no.pepega.oc.common.registry.OCRegistry;
import no.pepega.oc.api.internal.Tiered;

import java.util.List;

public class CPU extends ExtendedItem implements CPULike, Tiered {
    private final int tier;
    private Architecture architecture;

    public CPU(net.minecraft.item.Item.Settings settings, int tier) {
        super(settings);
        this.tier = tier;
        try {
            architecture = OCRegistry.getArchitectures().getFirst().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            architecture = null;
        }
    }

    @Override
    public List<Object> tooltipData() {
        return cpuTooltipData();
    }

    @Override
    public void tooltipExtended(ItemStack stack, List<Text> tooltip) {
        cpuTooltipExtended(stack, tooltip);
    }

    @Override
    public String registryName() {
        return super.registryName() + tier;
    }

    @Override
    public int tier() {
        return tier;
    }

    @Override
    public int cpuTier() {
        return tier;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return cpuUse(world, user, hand);
    }

    @Override
    public Class<? extends Architecture> architecture(ItemStack stack) {
        if (architecture == null) {
            return null;
        }
        return architecture.getClass();
    }
}
