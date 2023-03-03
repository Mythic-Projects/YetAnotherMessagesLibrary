package dev.peri.yetanothermessageslibrary.message.holder.impl;

import dev.peri.yetanothermessageslibrary.adventure.MiniComponent;
import dev.peri.yetanothermessageslibrary.adventure.RawComponent;
import dev.peri.yetanothermessageslibrary.message.SendableMessage;
import dev.peri.yetanothermessageslibrary.message.holder.SendableHolder;
import dev.peri.yetanothermessageslibrary.replace.ComponentReplacer;
import dev.peri.yetanothermessageslibrary.replace.Replaceable;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActionBarHolder extends SendableHolder {

    private final RawComponent message;

    public ActionBarHolder(@NotNull RawComponent message) {
        this.message = message;
    }

    public @NotNull RawComponent getMessage() {
        return this.message;
    }

    @Override
    public void send(@Nullable Locale locale, @NotNull Viewer viewer, @NotNull Replaceable... replacements) {
        viewer.sendActionBar(ComponentReplacer.replace(locale, this.message, replacements));
    }

    @Override
    public @NotNull SendableHolder copy(@NotNull Replaceable... replacements) {
        return new ActionBarHolder(ComponentReplacer.replaceRaw(this.message, replacements));
    }

    public static @NotNull SendableMessage message(@NotNull RawComponent message) {
        return SendableMessage.of(new ActionBarHolder(message));
    }

    public static @NotNull SendableMessage message(@NotNull String message) {
        return SendableMessage.of(new ActionBarHolder(MiniComponent.of(message)));
    }

}
