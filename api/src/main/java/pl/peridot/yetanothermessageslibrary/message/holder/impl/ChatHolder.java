package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.adventure.MiniComponent;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;
import pl.peridot.yetanothermessageslibrary.message.SendableMessage;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.replace.ComponentReplacer;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;

public class ChatHolder extends SendableHolder {

    protected boolean onlyConsole;
    private final List<RawComponent> messages = new ArrayList<>();

    public ChatHolder(boolean onlyConsole, @NotNull Collection<RawComponent> messages) {
        this.onlyConsole = onlyConsole;
        this.messages.addAll(messages);
    }

    public ChatHolder(boolean onlyConsole, @NotNull RawComponent... messages) {
        this(onlyConsole, Arrays.asList(messages));
    }

    @Override
    public boolean sendOnlyToConsole() {
        return this.onlyConsole;
    }

    public @NotNull List<RawComponent> getMessages() {
        return this.messages;
    }

    @Override
    public void send(@NotNull Audience audience, @NotNull Replaceable... replacements) {
        this.messages.forEach(message -> audience.sendMessage(ComponentReplacer.replace(message, replacements)));
    }

    public static @NotNull SendableMessage message(@NotNull RawComponent... messages) {
        return SendableMessage.of(new ChatHolder(false, messages));
    }

    public static @NotNull SendableMessage message(@NotNull String messages) {
        return SendableMessage.of(new ChatHolder(false, MiniComponent.of(messages)));
    }

}
