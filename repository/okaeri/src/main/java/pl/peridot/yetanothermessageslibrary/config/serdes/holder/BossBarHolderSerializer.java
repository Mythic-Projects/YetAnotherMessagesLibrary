package pl.peridot.yetanothermessageslibrary.config.serdes.holder;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.HashSet;
import net.kyori.adventure.bossbar.BossBar;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;

public class BossBarHolderSerializer implements ObjectSerializer<BossBarHolder> {

    private final SchedulerWrapper schedulerWrapper;

    public BossBarHolderSerializer(SchedulerWrapper schedulerWrapper) {
        this.schedulerWrapper = schedulerWrapper;
    }

    @Override
    public boolean supports(Class<? super BossBarHolder> type) {
        return BossBarHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(BossBarHolder holder, SerializationData data, GenericsDeclaration generics) {
        data.add("name", holder.getName());
        data.add("color", holder.getColor());
        data.add("overlay", holder.getOverlay());

        if (!holder.getFlags().isEmpty()) {
            data.addCollection("flags", holder.getFlags(), BossBar.Flag.class);
        }

        if (holder.getProgress() >= 0) {
            data.add("progress", holder.getProgress());
        }

        if (holder.getStay() >= 0) {
            data.add("stay", holder.getStay());
        }
    }

    @Override
    public BossBarHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        return BossBarHolder.builder(data.get("name", RawComponent.class))
                .progress(data.containsKey("progress") ? data.get("progress", float.class) : 1)
                .color(data.containsKey("color") ? data.get("color", BossBar.Color.class) : BossBar.Color.PINK)
                .overlay(data.containsKey("overlay") ? data.get("overlay", BossBar.Overlay.class) : BossBar.Overlay.PROGRESS)
                .addFlags(data.containsKey("flags") ? data.getAsList("flags", BossBar.Flag.class) : new HashSet<>())
                .stay(this.schedulerWrapper, data.containsKey("stay") ? data.get("stay", int.class) : -1)
                .build();
    }

}
