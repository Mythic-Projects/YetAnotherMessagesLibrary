package dev.peri.yetanothermessageslibrary.replace;

import dev.peri.yetanothermessageslibrary.adventure.RawComponent;
import java.util.Locale;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ComponentReplacer {

    private ComponentReplacer() {
    }

    @Contract(pure = true, value = "_, null, _ -> null")
    public static Component replace(@Nullable Locale locale, @Nullable Component text, @NotNull Replaceable... replacements) {
        if (text == null) {
            return null;
        }

        for (Replaceable replacement : replacements) {
            text = replacement.replace(text);
        }
        return text;
    }

    @Contract(pure = true, value = "null, _ -> null")
    public static Component replace(@Nullable Component text, @NotNull Replaceable... replacements) {
        return replace(null, text, replacements);
    }

    @Contract(pure = true)
    public static @NotNull Component replace(@Nullable Locale locale, @NotNull RawComponent text, @NotNull Replaceable... replacements) {
        return replace(locale, text.getComponent(), replacements);
    }

    @Contract(pure = true)
    public static @NotNull Component replace(@NotNull RawComponent text, @NotNull Replaceable... replacements) {
        return replace(null, text, replacements);
    }

}
