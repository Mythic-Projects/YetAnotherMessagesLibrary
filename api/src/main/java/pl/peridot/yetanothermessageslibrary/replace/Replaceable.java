package pl.peridot.yetanothermessageslibrary.replace;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface Replaceable {

    @NotNull String replace(@NotNull String text, boolean ignoreCase);

    default @NotNull String replace(@NotNull String text) {
        return this.replace(text, false);
    }

    @NotNull Component replace(@NotNull Component text);

}
