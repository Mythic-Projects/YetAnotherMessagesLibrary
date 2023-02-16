package dev.peri.yetanothermessageslibrary.replace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StringReplacer {

    private StringReplacer() {
    }

    @Contract(pure = true, value = "_, null, _, -> null")
    public static String replace(@Nullable Locale locale, @Nullable String text, @NotNull Collection<? extends Replaceable> replacements) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        for (Replaceable replacement : replacements) {
            text = replacement.replace(locale, text);
        }

        return text;
    }

    @Contract(pure = true, value = "null, _, -> null")
    public static String replace(@Nullable String text, @NotNull Collection<? extends Replaceable> replacements) {
        return replace(null, text, replacements);
    }

    @Contract(pure = true, value = "_, null, _, -> null")
    public static String replace(@Nullable Locale locale, @Nullable String text, @NotNull Replaceable... replacements) {
        return replace(locale, text, Arrays.asList(replacements));
    }

    @Contract(pure = true, value = "null, _, -> null")
    public static String replace(@Nullable String text, @NotNull Replaceable... replacements) {
        return replace(null, text, replacements);
    }

    @Contract(pure = true)
    public static @NotNull List<String> replace(@Nullable Locale locale, @NotNull List<String> text, @NotNull Replaceable... replacements) {
        List<String> result = new ArrayList<>();
        for (String line : text) {
            result.add(replace(locale, line, replacements));
        }
        return result;
    }

    @Contract(pure = true)
    public static @NotNull List<String> replace(@NotNull List<String> text, @NotNull Replaceable... replacements) {
        return replace(null, text, replacements);
    }

}
