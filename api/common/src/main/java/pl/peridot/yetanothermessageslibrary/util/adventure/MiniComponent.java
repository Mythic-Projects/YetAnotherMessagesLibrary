package pl.peridot.yetanothermessageslibrary.util.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class MiniComponent extends RawComponent {

    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private MiniComponent(@NotNull String raw, @NotNull Component component) {
        super(raw, component);
    }

    public static @NotNull MiniComponent of(@NotNull String raw) {
        return new MiniComponent(raw, MINI_MESSAGE.deserialize(raw));
    }

    public static @NotNull MiniComponent[] of(@NotNull String... raw) {
        MiniComponent[] miniComponents = new MiniComponent[raw.length];
        for (int i = 0; i < raw.length; i++) {
            miniComponents[i] = MiniComponent.of(raw[i]);
        }
        return miniComponents;
    }

    public static @NotNull MiniComponent ofLegacy(@NotNull String legacy) {
        return of(AdventureHelper.legacyToMiniMessage(legacy));
    }

    public static @NotNull MiniComponent[] ofLegacy(@NotNull String... legacy) {
        MiniComponent[] miniComponents = new MiniComponent[legacy.length];
        for (int i = 0; i < legacy.length; i++) {
            miniComponents[i] = MiniComponent.ofLegacy(legacy[i]);
        }
        return miniComponents;
    }

}
