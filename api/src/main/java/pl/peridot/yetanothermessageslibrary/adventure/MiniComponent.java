package pl.peridot.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public final class MiniComponent {

    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private MiniComponent() {
    }

    public static @NotNull RawComponent of(@NotNull String raw) {
        return new RawComponent(raw, MINI_MESSAGE.deserialize(raw));
    }

    public static @NotNull RawComponent[] of(@NotNull String... raw) {
        RawComponent[] miniComponents = new RawComponent[raw.length];
        for (int i = 0; i < raw.length; i++) {
            miniComponents[i] = MiniComponent.of(raw[i]);
        }
        return miniComponents;
    }

    public static @NotNull RawComponent ofLegacy(@NotNull String legacy) {
        return new RawComponent(legacy, MINI_MESSAGE.deserialize(AdventureHelper.legacyToMiniMessage(legacy)));
    }

    public static @NotNull RawComponent[] ofLegacy(@NotNull String... legacy) {
        RawComponent[] miniComponents = new RawComponent[legacy.length];
        for (int i = 0; i < legacy.length; i++) {
            miniComponents[i] = MiniComponent.ofLegacy(legacy[i]);
        }
        return miniComponents;
    }

}
