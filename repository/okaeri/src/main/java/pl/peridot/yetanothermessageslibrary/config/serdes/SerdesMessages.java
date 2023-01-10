package pl.peridot.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.ActionBarHolderTransformer;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.BossBarHolderSerializer;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.ChatSerializer;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.SoundHolderSerializer;
import pl.peridot.yetanothermessageslibrary.config.serdes.holder.TitleHolderSerializer;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;

public class SerdesMessages implements OkaeriSerdesPack {

    private final SchedulerWrapper schedulerWrapper;

    public SerdesMessages(@Nullable SchedulerWrapper schedulerWrapper) {
        this.schedulerWrapper = schedulerWrapper;
    }

    @Override
    public void register(SerdesRegistry registry) {
        // Serializers/Deserializers
        registry.register(new ChatSerializer());
        registry.register(new TitleHolderSerializer());
        registry.register(new BossBarHolderSerializer(this.schedulerWrapper));
        registry.register(new SoundHolderSerializer());
        registry.register(new SendableMessageSerializer());

        // Transformers
        registry.register(new RawComponentTransformer());
        registry.register(new KeyTransformer());
        registry.register(new ActionBarHolderTransformer());
    }

}
