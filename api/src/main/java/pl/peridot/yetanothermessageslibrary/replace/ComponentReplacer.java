package pl.peridot.yetanothermessageslibrary.replace;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;

public final class ComponentReplacer {

    private ComponentReplacer() {
    }

    @Contract(pure = true, value = "null, _ -> null")
    public static Component replace(@Nullable Component text, @NotNull Replaceable... replacements) {
        if (text == null) {
            return null;
        }

        for (Replaceable replacement : replacements) {
            text = replacement.replace(text);
        }
        return text;
    }

    @Contract(pure = true)
    public static @NotNull Component replace(@NotNull RawComponent text, @NotNull Replaceable... replacements) {
        return replace(text.getComponent(), replacements);
    }

}
