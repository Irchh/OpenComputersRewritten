package no.pepega.oc.common.item.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import no.pepega.oc.api.Driver;
import no.pepega.oc.api.Machine;
import no.pepega.oc.api.driver.DriverItem;
import no.pepega.oc.api.driver.item.MutableProcessor;
import no.pepega.oc.api.machine.Architecture;
import no.pepega.oc.util.Tooltip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static no.pepega.oc.OCSettings.CPUComponentByTier;

public interface CPULike {
    int cpuTier();

    default List<Object> cpuTooltipData() {
        return Collections.singletonList(CPUComponentByTier[cpuTier()]);
    }

    default void cpuTooltipExtended(ItemStack stack, List<Text> tooltip) {
        for (String curr: Tooltip.get("cpu.Architecture", Machine.getArchitectureName(architecture(stack)))) {
            tooltip.add(Text.literal(curr).setStyle(Tooltip.DefaultStyle));
        }
    }

    Class<? extends Architecture> architecture(ItemStack stack);

    default TypedActionResult<ItemStack> cpuUse(World world, PlayerEntity player, Hand hand) {
        if (player.isSneaking()) {
            if (!world.isClient()) {
                var stack = player.getStackInHand(hand);
                var driver = Driver.driverFor(stack);
                if (driver instanceof MutableProcessor processor) {
                    List<Class<? extends Architecture>> architectures = new ArrayList<>(processor.allArchitectures());
                    if (!architectures.isEmpty()) {
                        int currentIndex = architectures.indexOf(processor.architecture(stack));
                        int newIndex = (currentIndex + 1) % architectures.toArray().length;
                        Class<? extends Architecture> archClass = architectures.get(newIndex);
                        String archName = Machine.getArchitectureName(archClass);
                        processor.setArchitecture(stack, archClass);
                        player.sendMessage(Text.translatable(Tooltip.namespace + "tooltip.cpu.Architecture", archName), false);
                    }
                    player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }
}
