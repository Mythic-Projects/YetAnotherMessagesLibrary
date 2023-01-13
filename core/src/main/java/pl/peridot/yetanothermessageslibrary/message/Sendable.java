package pl.peridot.yetanothermessageslibrary.message;

import java.util.Locale;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;

public interface Sendable {

    default boolean sendOnlyToConsole() {
        return false;
    }

    void send(@Nullable Locale locale, @NotNull Audience audience, @NotNull Replaceable... replacements);

    default void send(@NotNull Audience audience, @NotNull Replaceable... replacements) {
        this.send(null, audience, replacements);
    }

    default void send(@Nullable Locale locale, @NotNull Audience audience, boolean console, @NotNull Replaceable... replacements) {
        if (this.sendOnlyToConsole() && !console) {
            return;
        }
        this.send(locale, audience, replacements);
    }

    default void send(@NotNull Audience audience, boolean console, @NotNull Replaceable... replacements) {
        this.send(null, audience, console, replacements);
    }

}
