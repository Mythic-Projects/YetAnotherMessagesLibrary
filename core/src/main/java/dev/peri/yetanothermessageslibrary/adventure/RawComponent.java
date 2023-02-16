package dev.peri.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class to store raw String and Component at the same time.
 * Helpful with serialization and deserialization of components.
 */
public class RawComponent {

    public static final RawComponent EMPTY = new RawComponent("", Component.empty());

    private final String raw;
    private final Component component;

    public RawComponent(@NotNull String raw, @NotNull Component component) {
        this.raw = raw;
        this.component = component;
    }

    public @NotNull String getRaw() {
        return this.raw;
    }

    public @NotNull Component getComponent() {
        return this.component;
    }

}
