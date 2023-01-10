package pl.peridot.yetanothermessageslibrary.config.serdes.holder;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import net.kyori.adventure.bossbar.BossBar;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;
import pl.peridot.yetanothermessageslibrary.util.adventure.RawComponent;

public class BossBarHolderSerializer implements ObjectSerializer<BossBarHolder> {

    private final SchedulerWrapper schedulerWrapper;

    public BossBarHolderSerializer(SchedulerWrapper schedulerWrapper) {
        this.schedulerWrapper = schedulerWrapper;
    }

    public BossBarHolderSerializer() {
        this(null);
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

        if (holder.getProgress() >= 0) {
            data.add("progress", holder.getProgress());
        }

        if (holder.getStay() >= 0) {
            data.add("stay", holder.getStay());
        }
    }

    @Override
    public BossBarHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        RawComponent name = data.get("name", RawComponent.class);
        float progress = data.containsKey("progress") ? data.get("progress", float.class) : 1;
        BossBar.Color color = data.get("color", BossBar.Color.class);
        BossBar.Overlay overlay = data.get("overlay", BossBar.Overlay.class);
        int stay = data.containsKey("stay") ? data.get("stay", int.class) : -1;

        return new BossBarHolder(name, progress, color, overlay, stay, this.schedulerWrapper);
    }

}
