package no.pepega.oc.common.item.util;

import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import no.pepega.oc.api.component.ComponentItem;
import no.pepega.oc.common.Tier;
import no.pepega.oc.util.Color;
import no.pepega.oc.util.Tooltip;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExtendedItem extends Item {
    public ExtendedItem(Settings settings) {
        super(settings);
    }

    public String registryName() {
        return unlocalizedName();
    }

    public String unlocalizedName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public Optional<String> tooltipName() {
        return Optional.of(unlocalizedName());
    }

    public List<Object> tooltipData() {
        return new ArrayList<>();
    }

    @Override
    public Text getName() {
        if (this instanceof ComponentItem item && item.tier() > Tier.One && item.tier() <= Tier.Four) {
            return super.getName().copy().withColor(Color.rgbValues.get(Color.byTier.get(item.tier())));
        }
        return super.getName();
    }

    @Override
    public Text getName(ItemStack stack) {
        if (this instanceof ComponentItem item && item.tier() > Tier.One && item.tier() <= Tier.Four) {
            return super.getName(stack).copy().withColor(Color.rgbValues.get(Color.byTier.get(item.tier())));
        }
        return super.getName(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (tooltipName().isPresent()) {
            for (String curr: Tooltip.get(tooltipName().get(), tooltipData().toArray(new Object[0]))) {
                tooltip.add(Text.literal(curr).setStyle(Tooltip.DefaultStyle));
            }
            tooltipExtended(stack, tooltip);
        }
        else {
            for (String curr: Tooltip.get(getClass().getSimpleName().toLowerCase())) {
                tooltip.add(Text.literal(curr).setStyle(Tooltip.DefaultStyle));
            }
        }
        //tooltipCosts(stack, tooltip);
        //tooltipExtended(stack, tooltip);
    }

    // For stuff that goes to the normal 'extended' tooltip, before the costs.
    public void tooltipExtended(ItemStack stack, List<Text> tooltip) {}

    public void tooltipCosts(ItemStack stack, List<Text> tooltip) {
        /*stack.getComponents();
        if (stack.hasTag() && stack.getTag().contains(Settings.namespace + "data")) {
            CompoundNBT data = stack.getTag().getCompound(Settings.namespace + "data");
            if (data.contains("node") && data.getCompound("node").contains("address")) {
                tooltip.add(new StringTextComponent("§8" + data.getCompound("node").getString("address").substring(0, 13) + "...§7"));
            }
        }*/
    }
}
