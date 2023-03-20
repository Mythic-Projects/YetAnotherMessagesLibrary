package dev.peri.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class AdventureHelper {

    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacySection();

    public static @NotNull Component legacyToComponent(@NotNull String legacyText) {
        return LEGACY_SERIALIZER.deserialize(legacyText); //TODO: Find better way without using legacy serializer
    }

}
