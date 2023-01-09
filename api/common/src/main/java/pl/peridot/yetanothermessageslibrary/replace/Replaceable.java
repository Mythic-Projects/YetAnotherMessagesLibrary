package pl.peridot.yetanothermessageslibrary.replace;

import net.kyori.adventure.text.Component;

public interface Replaceable {

    String replace(String text, boolean ignoreCase);

    default String replace(String text) {
        return this.replace(text, false);
    }

    Component replace(Component text);

}
