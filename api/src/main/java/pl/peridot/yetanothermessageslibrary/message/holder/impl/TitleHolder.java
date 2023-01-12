package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.adventure.MiniComponent;
import pl.peridot.yetanothermessageslibrary.message.SendableMessage;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.replace.ComponentReplacer;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;

public class TitleHolder extends SendableHolder {

    private final RawComponent title;
    private final RawComponent subTitle;
    private final Times times;

    public TitleHolder(@NotNull RawComponent title, @NotNull RawComponent subTitle, @NotNull Times times) {
        this.title = title;
        this.subTitle = subTitle;
        this.times = times;
    }

    public TitleHolder(@NotNull RawComponent title, @NotNull RawComponent subTitle, int fadeIn, int stay, int fadeOut) {
        this(title, subTitle, Times.times(Ticks.duration(fadeIn), Ticks.duration(stay), Ticks.duration(fadeOut)));
    }

    public @NotNull RawComponent getTitle() {
        return this.title;
    }

    public @NotNull RawComponent getSubTitle() {
        return this.subTitle;
    }

    public @NotNull Times getTimes() {
        return this.times;
    }

    public @NotNull Title prepareTitle(@NotNull Replaceable... replacements) {
        return Title.title(
                ComponentReplacer.replace(this.title, replacements),
                ComponentReplacer.replace(this.subTitle, replacements),
                this.times
        );
    }

    @Override
    public void send(@NotNull Audience audience, @NotNull Replaceable... replacements) {
        audience.showTitle(this.prepareTitle(replacements));
    }

    public static @NotNull SendableMessage message(@NotNull RawComponent title, @NotNull RawComponent subTitle, int fadeIn, int stay, int fadeOut) {
        return SendableMessage.of(new TitleHolder(title, subTitle, fadeIn, stay, fadeOut));
    }

    public static @NotNull SendableMessage message(@NotNull String title, @NotNull String subTitle, int fadeIn, int stay, int fadeOut) {
        return SendableMessage.of(new TitleHolder(MiniComponent.of(title), MiniComponent.of(subTitle), fadeIn, stay, fadeOut));
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private RawComponent title = RawComponent.EMPTY;
        private RawComponent subTitle = RawComponent.EMPTY;
        private Times times = Times.times(Ticks.duration(10), Ticks.duration(70), Ticks.duration(20));

        private Builder() {
        }

        @Contract("_ -> this")
        public Builder title(@NotNull RawComponent title) {
            this.title = title;
            return this;
        }

        @Contract("_ -> this")
        public Builder title(@NotNull String title) {
            return this.title(MiniComponent.ofLegacy(title));
        }

        @Contract("_ -> this")
        public Builder subTitle(@NotNull RawComponent subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        @Contract("_ -> this")
        public Builder subTitle(@NotNull String subTitle) {
            return this.subTitle(MiniComponent.ofLegacy(subTitle));
        }

        @Contract("_ -> this")
        public Builder times(@NotNull Times times) {
            this.times = times;
            return this;
        }

        @Contract("_, _, _ -> this")
        public Builder times(int fadeIn, int stay, int fadeOut) {
            return this.times(Times.times(Ticks.duration(fadeIn), Ticks.duration(stay), Ticks.duration(fadeOut)));
        }

        public @NotNull TitleHolder build() {
            return new TitleHolder(this.title, this.subTitle, this.times);
        }

    }

}
