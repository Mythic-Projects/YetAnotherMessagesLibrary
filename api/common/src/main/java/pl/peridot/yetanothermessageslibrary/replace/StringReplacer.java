package pl.peridot.yetanothermessageslibrary.replace;

import java.util.Arrays;
import java.util.Collection;

public final class StringReplacer {

    private StringReplacer() {
    }

    public static String replace(String text, Collection<? extends Replaceable> replacements, boolean ignoreCase) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        for (Replaceable replacement : replacements) {
            text = replacement.replace(text, ignoreCase);
        }

        return text;
    }

    public static String replace(String text, Collection<? extends Replaceable> replacements) {
        return replace(text, replacements, false);
    }

    public static String replaceIgnoreCase(String text, Collection<? extends Replaceable> replacements) {
        return replace(text, replacements, true);
    }

    public static String replace(String text, Replaceable... replacements) {
        return replace(text, Arrays.asList(replacements));
    }

    public static String replaceIgnoreCase(String text, Replaceable... replacements) {
        return replaceIgnoreCase(text, Arrays.asList(replacements));
    }

}
