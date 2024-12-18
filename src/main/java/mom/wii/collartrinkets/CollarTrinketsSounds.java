package mom.wii.collartrinkets;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

import static mom.wii.collartrinkets.CollarTrinkets.id;

public class CollarTrinketsSounds {
    public static void initialize() {}

    public static final SoundEvent COLLAR_BELL = Registry.register(Registries.SOUND_EVENT, id("collar_bell"), SoundEvent.of(id("collar_bell")));
}
