package no.pepega.oc.common.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import no.pepega.oc.common.block.blockentity.CaseEntity;

public class Networking {
    public static void init() {
        PayloadTypeRegistry.playC2S().register(PowerButtonPressedPayload.PACKET_ID, PowerButtonPressedPayload.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PowerButtonPressedPayload.PACKET_ID, ((payload, context) -> {
            var world = context.player().getServerWorld();
            var entity = world.getBlockEntity(payload.pos());
            if (entity instanceof CaseEntity caseEntity) {
                caseEntity.onPowerPressed();
            }
        }));
    }
}
