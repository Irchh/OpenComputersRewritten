package no.pepega.oc.common.init;

import net.minecraft.component.ComponentType;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class OCDataComponentTypes {
    public static ComponentType<NbtComponent> LEGACY_NBT_COMPAT;

    public static void init() {
        LEGACY_NBT_COMPAT = OCDataComponentTypes.register("nbt_compat", builder -> builder.codec(NbtComponent.CODEC));
    }

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, ((ComponentType.Builder<T>)builderOperator.apply(ComponentType.builder())).build());
    }
}
