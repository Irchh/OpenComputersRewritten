package no.pepega.oc.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import no.pepega.oc.OpenComputersRewritten;

public class Sounds {
    public static final Identifier COMPUTER_RUNNING_ID = new Identifier(OpenComputersRewritten.identifier, "computer_running");
    public static SoundEvent COMPUTER_RUNNING_EVENT = SoundEvent.of(COMPUTER_RUNNING_ID);

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, COMPUTER_RUNNING_ID, COMPUTER_RUNNING_EVENT);
    }
}
