package dev.peri.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class AdventureHelper {

    private AdventureHelper() {
    }

    private static final LegacyComponentSerializer LEGACY_SECTION = LegacyComponentSerializer.legacySection();
    private static final LegacyComponentSerializer LEGACY_AMPERSAND = LegacyComponentSerializer.legacyAmpersand();

    public static @NotNull Component legacyToComponent(@NotNull String legacyText) {
        return LEGACY_SECTION.deserialize(legacyText); //TODO: Find better way without using legacy serializer
    }

    public static @NotNull String componentToAmpersandString(@NotNull Component component) {
        return LEGACY_AMPERSAND.serialize(component); //TODO: Find better way without using legacy serializer
    }

}
