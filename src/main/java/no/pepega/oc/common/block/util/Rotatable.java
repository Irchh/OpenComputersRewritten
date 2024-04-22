package no.pepega.oc.common.block.util;

import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;

public interface Rotatable {
    DirectionProperty Facing = DirectionProperty.of("facing", Direction.Type.HORIZONTAL);
    DirectionProperty Pitch = DirectionProperty.of("pitch", d -> d.getAxis() == Direction.Axis.Y || d == Direction.NORTH);
    DirectionProperty Yaw = DirectionProperty.of("yaw", Direction.Type.HORIZONTAL);
}
