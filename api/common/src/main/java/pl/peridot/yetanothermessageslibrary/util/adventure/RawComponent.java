package pl.peridot.yetanothermessageslibrary.util.adventure;

import net.kyori.adventure.text.Component;

/**
 * Utility class to store raw String and Component at the same time.
 * Helpful with serialization and deserialization of components.
 */
public class RawComponent {

    private final String raw;
    private final Component component;

    public RawComponent(String raw, Component component) {
        this.raw = raw;
        this.component = component;
    }

    public String getRaw() {
        return this.raw;
    }

    public Component getComponent() {
        return this.component;
    }

}
