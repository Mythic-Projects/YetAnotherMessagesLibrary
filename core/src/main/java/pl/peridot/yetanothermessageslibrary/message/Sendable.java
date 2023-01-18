package pl.peridot.yetanothermessageslibrary.message;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.viewer.Viewer;

public interface Sendable {

    void send(@Nullable Locale locale, @NotNull Viewer viewer, @NotNull Replaceable... replacements);

    default void send(@NotNull Viewer viewer, @NotNull Replaceable... replacements) {
        this.send(null, viewer, replacements);
    }

}
