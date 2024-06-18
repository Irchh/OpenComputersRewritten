package no.pepega.oc.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import no.pepega.oc.api.machine.Architecture;
import no.pepega.oc.common.Tier;
import no.pepega.oc.common.item.util.CPULike;
import no.pepega.oc.common.item.util.ExtendedItem;
import no.pepega.oc.common.item.util.GPULike;
import no.pepega.oc.common.registry.OCRegistry;
import no.pepega.oc.api.internal.Tiered;

import java.util.ArrayList;
import java.util.List;

public class APU extends ExtendedItem implements CPULike, GPULike, Tiered {
    private final int tier;
    private Architecture architecture;

    public APU(net.minecraft.item.Item.Settings settings, int tier) {
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
        ArrayList<Object> combinedData = new ArrayList<>();
        combinedData.addAll(cpuTooltipData());
        combinedData.addAll(gpuTooltipData());
        return combinedData;
    }

    @Override
    public void tooltipExtended(ItemStack stack, List<Text> tooltip) {
        cpuTooltipExtended(stack, tooltip);
    }

    @Override
    public String registryName() {
        if (tier >= Tier.Three) {
            return super.registryName() + "creative";
        }
        return super.registryName() + tier;
    }

    @Override
    public int tier() {
        return tier;
    }

    @Override
    public int cpuTier() {
        return Math.min(Tier.Three, tier + 1);
    }

    @Override
    public int gpuTier() {
        return Math.min(Tier.Three, tier);
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
