package no.pepega.oc.common.driver;

import com.google.common.base.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import no.pepega.oc.api.driver.DriverBlock;
import no.pepega.oc.api.driver.NamedBlock;
import no.pepega.oc.api.network.ManagedEnvironment;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record CompoundBlockDriver(List<DriverBlock> sidedBlocks) implements DriverBlock {
    @Override
    public CompoundBlockEnvironment createEnvironment(World world, BlockPos pos, Direction side) {
        var list = sidedBlocks.stream().map(driver -> {
            var environment = driver.createEnvironment(world, pos, side);
            if (environment != null) {
                return new Pair<>(driver.getClass().getName(), environment);
            }
            return null;
        }).filter(Objects::nonNull).toList();
        return list.isEmpty() ? null : new CompoundBlockEnvironment(cleanName(tryGetName(world, pos, list.stream().map(Pair::getRight))), list);
    }

    @Override
    public boolean worksWith(World world, BlockPos pos, Direction side) {
        return false;
    }

    private String tryGetName(World world, BlockPos pos, Stream<ManagedEnvironment> environments) {
        var preferred = environments
                .filter(e -> e instanceof NamedBlock)
                .map(e -> (NamedBlock)e)
                .sorted(Comparator.comparingInt(NamedBlock::priority))
                .toList().getLast();
        if (preferred != null) {
            return preferred.preferredName();
        }
        try {
            var block = world.getBlockState(pos).getBlock();
            if (block.asItem() != null) {
                return new ItemStack(block, 1).getTranslationKey().substring("tile.".length());
            }
        } catch (Throwable ignored) {}
        try {
            if (world.getBlockEntity(pos) instanceof BlockEntity be) {
                return be.getType().getRegistryEntry().getIdAsString();
            }
        } catch (Throwable ignored) {}
        return "component";
    }

    private String cleanName(String name) {
        var safeStart = (name.matches("^[^a-zA-Z_]")) ? "_" + name : name;
        var identifier = safeStart.replaceAll("[^\\w_]", "_").trim();
        if (Strings.isNullOrEmpty(identifier))
            return "component";
        else
            return identifier.toLowerCase();
    }
}
