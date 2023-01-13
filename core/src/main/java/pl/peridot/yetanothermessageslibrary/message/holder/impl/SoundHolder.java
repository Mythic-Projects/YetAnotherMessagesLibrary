package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;

public class SoundHolder extends SendableHolder {

    private final Sound sound;
    private final boolean stopOtherSounds;

    public SoundHolder(@NotNull Sound sound, boolean stopOtherSounds) {
        this.sound = sound;
        this.stopOtherSounds = stopOtherSounds;
    }

    public @NotNull Sound getSound() {
        return this.sound;
    }

    public boolean stopOtherSounds() {
        return this.stopOtherSounds;
    }

    @Override
    public void send(@NotNull Audience audience, @NotNull Replaceable... replacements) {
        if (this.stopOtherSounds) {
            audience.stopSound(SoundStop.all());
        }
        audience.playSound(this.sound);
    }

}
