package pl.peridot.yetanothermessageslibrary.replace;

import java.util.Arrays;
import java.util.Collection;
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

}
