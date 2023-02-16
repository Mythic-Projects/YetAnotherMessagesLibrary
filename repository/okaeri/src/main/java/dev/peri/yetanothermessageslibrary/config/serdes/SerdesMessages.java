package dev.peri.yetanothermessageslibrary.config.serdes;

import dev.peri.yetanothermessageslibrary.config.serdes.holder.ActionBarHolderTransformer;
import dev.peri.yetanothermessageslibrary.config.serdes.holder.BossBarHolderSerializer;
import dev.peri.yetanothermessageslibrary.config.serdes.holder.ChatSerializer;
import dev.peri.yetanothermessageslibrary.config.serdes.holder.SoundHolderSerializer;
import dev.peri.yetanothermessageslibrary.config.serdes.holder.TitleHolderSerializer;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;

public class SerdesMessages implements OkaeriSerdesPack {

    @Override
    public void register(SerdesRegistry registry) {
        // Messages
        registry.register(new ChatSerializer());
        registry.register(new ActionBarHolderTransformer());
        registry.register(new TitleHolderSerializer());
        registry.register(new BossBarHolderSerializer());
        registry.register(new SoundHolderSerializer());
        registry.register(new SendableMessageSerializer());

        // Utilities
        registry.register(new RawComponentTransformer());
        registry.register(new KeyTransformer());
    }

}
