package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.replace.ComponentReplacer;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;

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

}
