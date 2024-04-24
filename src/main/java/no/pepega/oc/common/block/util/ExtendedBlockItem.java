package no.pepega.oc.common.block.util;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import no.pepega.oc.common.Tier;
import no.pepega.oc.util.Color;

public class ExtendedBlockItem extends BlockItem {
    public ExtendedBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Text getName() {
        if (this.getBlock() instanceof no.pepega.oc.common.block.util.Tier block && block.tier() > no.pepega.oc.common.Tier.One && block.tier() <= no.pepega.oc.common.Tier.Four) {
            return super.getName().copy().withColor(Color.rgbValues.get(Color.byTier.get(block.tier())));
        }
        return super.getName();
    }

    @Override
    public Text getName(ItemStack stack) {
        if (this.getBlock() instanceof no.pepega.oc.common.block.util.Tier item && item.tier() > no.pepega.oc.common.Tier.One && item.tier() <= Tier.Four) {
            return super.getName(stack).copy().withColor(Color.rgbValues.get(Color.byTier.get(item.tier())));
        }
        return super.getName(stack);
    }
}
