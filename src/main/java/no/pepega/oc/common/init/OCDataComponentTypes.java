package no.pepega.oc.common.init;

import net.minecraft.component.DataComponentType;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class OCDataComponentTypes {
    public static DataComponentType<NbtComponent> LEGACY_NBT_COMPAT;

    public static void init() {
        LEGACY_NBT_COMPAT = OCDataComponentTypes.register("nbt_compat", builder -> builder.codec(NbtComponent.CODEC));
    }

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, ((DataComponentType.Builder<T>)builderOperator.apply(DataComponentType.builder())).build());
    }
}
