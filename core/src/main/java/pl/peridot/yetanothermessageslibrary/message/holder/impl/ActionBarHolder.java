package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.adventure.MiniComponent;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;
import pl.peridot.yetanothermessageslibrary.message.SendableMessage;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.replace.ComponentReplacer;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;

public class ActionBarHolder extends SendableHolder {

    private final RawComponent message;

    public ActionBarHolder(@NotNull RawComponent message) {
        this.message = message;
    }

    public @NotNull RawComponent getMessage() {
        return this.message;
    }

    @Override
    public void send(@NotNull Audience audience, @NotNull Replaceable... replacements) {
        audience.sendActionBar(ComponentReplacer.replace(this.message, replacements));
    }

    public static @NotNull SendableMessage message(@NotNull RawComponent message) {
        return SendableMessage.of(new ActionBarHolder(message));
    }

    public static @NotNull SendableMessage message(@NotNull String message) {
        return SendableMessage.of(new ActionBarHolder(MiniComponent.of(message)));
    }

}
