package pl.peridot.yetanothermessageslibrary.replace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StringReplacer {

    private StringReplacer() {
    }

    @Contract(pure = true, value = "null, _, _ -> null")
    public static String replace(@Nullable String text, @NotNull Collection<? extends Replaceable> replacements, boolean ignoreCase) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        for (Replaceable replacement : replacements) {
            text = replacement.replace(text, ignoreCase);
        }

        return text;
    }

    @Contract(pure = true, value = "null, _ -> null")
    public static String replace(@Nullable String text, @NotNull Collection<? extends Replaceable> replacements) {
        return replace(text, replacements, false);
    }

    @Contract(pure = true, value = "null, _, -> null")
    public static String replaceIgnoreCase(@Nullable String text, @NotNull Collection<? extends Replaceable> replacements) {
        return replace(text, replacements, true);
    }

    @Contract(pure = true, value = "null, _, -> null")
    public static String replace(@Nullable String text, @NotNull Replaceable... replacements) {
        return replace(text, Arrays.asList(replacements));
    }

    @Contract(pure = true, value = "null, _, -> null")
    public static String replaceIgnoreCase(@Nullable String text, @NotNull Replaceable... replacements) {
        return replaceIgnoreCase(text, Arrays.asList(replacements));
    }

    @Contract(pure = true)
    public static @NotNull List<String> replace(@NotNull List<String> text, @NotNull Replaceable... replacements) {
        List<String> result = new ArrayList<>();
        for (String line : text) {
            result.add(replace(line, replacements));
        }
        return result;
    }

}
