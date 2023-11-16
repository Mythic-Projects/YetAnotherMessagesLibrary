package org.mythicprojects.yetanothermessageslibrary.message.holder.impl;

import java.util.Locale;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;
import org.mythicprojects.yetanothermessageslibrary.message.SendableMessage;
import org.mythicprojects.yetanothermessageslibrary.message.holder.SendableHolder;
import org.mythicprojects.yetanothermessageslibrary.replace.ComponentReplacer;
import org.mythicprojects.yetanothermessageslibrary.replace.Replaceable;
import org.mythicprojects.yetanothermessageslibrary.viewer.Viewer;

public class ActionBarHolder extends SendableHolder {

    private final Component message;

    public ActionBarHolder(@NotNull Component message) {
        this.message = message;
    }

    public @NotNull Component getMessage() {
        return this.message;
    }

    @Override
    public void send(@Nullable Locale locale, @NotNull Viewer viewer, @NotNull Replaceable... replacements) {
        viewer.sendActionBar(ComponentReplacer.replace(locale, this.message, replacements));
    }

    @Override
    public @NotNull SendableHolder copy(@NotNull Replaceable... replacements) {
        return new ActionBarHolder(ComponentReplacer.replace(this.message, replacements));
    }

    public static @NotNull SendableMessage message(@NotNull Component message) {
        return SendableMessage.of(new ActionBarHolder(message));
    }

    public static @NotNull SendableMessage message(@NotNull String message) {
        return SendableMessage.of(new ActionBarHolder(GlobalAdventureSerializer.deserialize(message)));
    }

}
