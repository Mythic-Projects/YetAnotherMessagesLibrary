package dev.peri.yetanothermessageslibrary.config.serdes.holder;

import dev.peri.yetanothermessageslibrary.message.holder.impl.SoundHolder;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

public class SoundHolderSerializer implements ObjectSerializer<SoundHolder> {

    @Override
    public boolean supports(Class<? super SoundHolder> type) {
        return SoundHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(SoundHolder holder, SerializationData data, GenericsDeclaration generics) {
        Sound sound = holder.getSound();
        data.add("name", sound.name().asString());
        data.add("source", sound.source());
        data.add("volume", sound.volume());
        data.add("pitch", sound.pitch());
        if (holder.stopOtherSounds()) {
            data.add("stop-other-sounds", true);
        }
    }

    @Override
    public SoundHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        Key name = data.get("name", Key.class);
        Sound.Source source = data.get("source", Sound.Source.class);
        float volume = data.get("volume", float.class);
        float pitch = data.get("pitch", float.class);
        boolean stopOtherSounds = data.containsKey("stop-other-sounds")
                ? data.get("stop-other-sounds", boolean.class)
                : false;

        return new SoundHolder(Sound.sound(name, source, volume, pitch), stopOtherSounds);
    }

}
