package pl.peridot.yetanothermessageslibrary.replace;

import net.kyori.adventure.text.Component;
import pl.peridot.yetanothermessageslibrary.util.adventure.RawComponent;

public final class ComponentReplacer {

    private ComponentReplacer() {
    }

    public static Component replace(Component text, Replaceable... replacements) {
        for (Replaceable replacement : replacements) {
            text = replacement.replace(text);
        }
        return text;
    }

    public static Component replace(RawComponent text, Replaceable... replacements) {
        return replace(text.getComponent(), replacements);
    }

}
