package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.NotNull;
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

}
