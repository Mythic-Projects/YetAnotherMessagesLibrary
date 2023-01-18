package pl.peridot.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

/**
 * Some utility class to make MiniMessage optional
 */
public final class MiniComponent {

    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private MiniComponent() {
    }

    /**
     * Parse a string to {@link RawComponent} using {@link MiniMessage}.
     *
     * @param raw the string to parse.
     * @return the parsed component.
     */
    public static @NotNull RawComponent of(@NotNull String raw) {
        return new RawComponent(raw, MINI_MESSAGE.deserialize(raw));
    }

    /**
     * Parse multiple strings to {@link RawComponent} using {@link MiniMessage}.
     *
     * @param raw array of strings to parse.
     * @return array of parsed components.
     */
    public static @NotNull RawComponent[] of(@NotNull String... raw) {
        RawComponent[] miniComponents = new RawComponent[raw.length];
        for (int i = 0; i < raw.length; i++) {
            miniComponents[i] = MiniComponent.of(raw[i]);
        }
        return miniComponents;
    }

    /**
     * Replace legacy color codes (&amp;[0-9A-Fa-fK-Ok-oRr]) and hex colors codes (&amp;#[a-fA-F0-9]{6}) in text with mini message codes.
     * Then parse the text to {@link RawComponent} using {@link MiniMessage}.
     *
     * @param legacy the string to parse.
     * @return the parsed component.
     */
    public static @NotNull RawComponent ofLegacy(@NotNull String legacy) {
        return new RawComponent(legacy, MINI_MESSAGE.deserialize(AdventureHelper.legacyToMiniMessage(legacy)));
    }

    /**
     * Replace legacy color codes (&amp;[0-9A-Fa-fK-Ok-oRr]) and hex colors codes (&amp;#[a-fA-F0-9]{6}) in text with mini message codes.
     * Then parse multiple strings to {@link RawComponent} using {@link MiniMessage}.
     *
     * @param legacy array of strings to parse.
     * @return array of parsed components.
     */
    public static @NotNull RawComponent[] ofLegacy(@NotNull String... legacy) {
        RawComponent[] miniComponents = new RawComponent[legacy.length];
        for (int i = 0; i < legacy.length; i++) {
            miniComponents[i] = MiniComponent.ofLegacy(legacy[i]);
        }
        return miniComponents;
    }

}
