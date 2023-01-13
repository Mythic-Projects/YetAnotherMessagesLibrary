package pl.peridot.yetanothermessageslibrary.message;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;

public interface Sendable {

    default boolean sendOnlyToConsole() {
        return false;
    }

    void send(@NotNull Audience audience, @NotNull Replaceable... replacements);

    default void send(@NotNull Audience audience, boolean console, @NotNull Replaceable... replacements) {
        if (this.sendOnlyToConsole() && !console) {
            return;
        }
        this.send(audience, replacements);
    }

}
