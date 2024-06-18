package no.pepega.oc.common.networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import no.pepega.oc.OpenComputersRewritten;

public record PowerButtonPressedPayload(BlockPos pos) implements CustomPayload {
    public static final CustomPayload.Id<PowerButtonPressedPayload> PACKET_ID = new CustomPayload.Id<>(Identifier.of(OpenComputersRewritten.identifier, "power_button_pressed"));
    public static final PacketCodec<RegistryByteBuf, PowerButtonPressedPayload> PACKET_CODEC = BlockPos.PACKET_CODEC.xmap(PowerButtonPressedPayload::new, PowerButtonPressedPayload::pos).cast();

    public BlockPos get() {
        return this.pos;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
