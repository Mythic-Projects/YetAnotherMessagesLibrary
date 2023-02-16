package dev.peri.yetanothermessageslibrary.message.holder.impl;

import dev.peri.yetanothermessageslibrary.adventure.MiniComponent;
import dev.peri.yetanothermessageslibrary.adventure.RawComponent;
import dev.peri.yetanothermessageslibrary.message.SendableMessage;
import dev.peri.yetanothermessageslibrary.message.holder.SendableHolder;
import dev.peri.yetanothermessageslibrary.replace.ComponentReplacer;
import dev.peri.yetanothermessageslibrary.replace.Replaceable;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public boolean sendOnlyToConsole() {
        return this.onlyConsole;
    }

    public @NotNull List<RawComponent> getMessages() {
        return this.messages;
    }

    @Override
    public void send(@Nullable Locale locale, @NotNull Viewer viewer, @NotNull Replaceable... replacements) {
        if (this.onlyConsole && !viewer.isConsole()) {
            return;
        }

        List<Component> finalMessage = this.messages.stream()
                .map(message -> ComponentReplacer.replace(locale, message, replacements))
                .collect(Collectors.toList());
        viewer.sendChatMessage(finalMessage);
    }

    public static @NotNull SendableMessage message(@NotNull RawComponent... messages) {
        return SendableMessage.of(new ChatHolder(false, messages));
    }

    public static @NotNull SendableMessage message(@NotNull String... messages) {
        return SendableMessage.of(new ChatHolder(false, MiniComponent.of(messages)));
    }

}
