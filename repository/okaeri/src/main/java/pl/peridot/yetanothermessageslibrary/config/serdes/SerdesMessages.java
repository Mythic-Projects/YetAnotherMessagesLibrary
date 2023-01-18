package pl.peridot.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.ActionBarHolderTransformer;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.BossBarHolderSerializer;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.ChatSerializer;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.SoundHolderSerializer;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.TitleHolderSerializer;

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
