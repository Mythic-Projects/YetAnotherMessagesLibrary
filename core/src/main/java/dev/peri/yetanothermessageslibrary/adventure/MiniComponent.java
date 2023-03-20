package dev.peri.yetanothermessageslibrary.adventure;

import java.util.regex.Pattern;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Some utility class to make MiniMessage optional
 */
public final class MiniComponent {

    private static final Pattern SECTION_COLOR_PATTERN = Pattern.compile("(?i)§([0-9A-FK-OR])");
    private static final Pattern ALL_TEXT_PATTERN = Pattern.compile(".*");

    private static final Pattern HEX_TO_LEGACY_PATTERN = Pattern.compile("&#([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})");
    private static final String LEGACY_HEX_REPLACEMENT = "&x&$1&$2&$3&$4&$5&$6";

    private static final LegacyComponentSerializer AMPERSAND_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    private static final TextReplacementConfig COLOR_REPLACEMENTS = TextReplacementConfig.builder()
            .match(ALL_TEXT_PATTERN)
            .replacement((result, input) -> AMPERSAND_SERIALIZER.deserialize(result.group()))
            .build();
    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .preProcessor(text -> {
                String processedText = HEX_TO_LEGACY_PATTERN.matcher(text).replaceAll(LEGACY_HEX_REPLACEMENT);  // convert simple hex format to legacy
                processedText = SECTION_COLOR_PATTERN.matcher(processedText).replaceAll("&$1"); // convert section to ampersand
                return processedText;
            })
            .postProcessor(component -> component.replaceText(COLOR_REPLACEMENTS))
            .build();

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
        return new RawComponent(legacy, MINI_MESSAGE.deserialize(legacy));
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
