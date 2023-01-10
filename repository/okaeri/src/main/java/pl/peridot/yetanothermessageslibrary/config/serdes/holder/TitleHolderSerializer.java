package pl.peridot.yetanothermessageslibrary.config.serdes.holder;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.time.Duration;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.util.Ticks;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.TitleHolder;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;

public class TitleHolderSerializer implements ObjectSerializer<TitleHolder> {

    @Override
    public boolean supports(Class<? super TitleHolder> type) {
        return TitleHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(TitleHolder holder, SerializationData data, GenericsDeclaration generics) {
        if (!holder.getTitle().getRaw().isEmpty()) {
            data.add("title", holder.getTitle().getRaw());
        }
        if (!holder.getSubTitle().getRaw().isEmpty()) {
            data.add("subtitle", holder.getSubTitle().getRaw());
        }

        Times times = holder.getTimes();
        int fadeIn = ticksFromDuration(times.fadeIn());
        int stay = ticksFromDuration(times.stay());
        int fadeOut = ticksFromDuration(times.fadeOut());

        if (fadeIn > 0) {
            data.add("fade-in", fadeIn);
        }

        if (stay > 0) {
            data.add("stay", stay);
        }

        if (fadeOut > 0) {
            data.add("fade-out", fadeOut);
        }
    }

    @Override
    public TitleHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        RawComponent title = RawComponent.EMPTY;
        RawComponent subtitle = RawComponent.EMPTY;

        if (data.containsKey("title")) {
            title = data.get("title", RawComponent.class);
        }

        if (data.containsKey("subtitle")) {
            subtitle = data.get("subtitle", RawComponent.class);
        }

        int fadeIn = 0;
        int stay = 0;
        int fadeOut = 0;

        if (data.containsKey("fade-in")) {
            fadeIn = data.get("fade-in", Integer.class);
        }

        if (data.containsKey("stay")) {
            stay = data.get("stay", Integer.class);
        }

        if (data.containsKey("fade-out")) {
            fadeOut = data.get("fade-out", Integer.class);
        }

        return new TitleHolder(title, subtitle, fadeIn, stay, fadeOut);
    }

    public static int ticksFromDuration(Duration duration) {
        return (int) (duration.toMillis() / Ticks.SINGLE_TICK_DURATION_MS);
    }

}
