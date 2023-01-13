package pl.peridot.yetanothermessageslibrary.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.MiniComponent;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.ChatHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.SoundHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.TitleHolder;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;

public class SendableMessage implements Sendable {

    private final List<SendableHolder> holders = new ArrayList<>();

    protected SendableMessage(@NotNull Collection<SendableHolder> holders) {
        this.holders.addAll(holders);
    }

    public @NotNull List<SendableHolder> getHolders() {
        return new ArrayList<>(this.holders);
    }

    public @NotNull <T extends SendableHolder> List<T> getHolders(@NotNull Class<T> type) {
        return this.holders.stream()
                .filter(holder -> holder.getClass().isAssignableFrom(type))
                .map(holder -> (T) holder)
                .collect(Collectors.toList());
    }

    public void send(@NotNull Audience audience, @NotNull Replaceable... replacements) {
        this.holders.forEach(holder -> holder.send(audience, replacements));
    }

    public void send(@NotNull Audience audience, boolean console, @NotNull Replaceable... replacements) {
        this.holders.forEach(holder -> holder.send(audience, console, replacements));
    }

    public static @NotNull SendableMessage of(@NotNull Collection<SendableHolder> holders) {
        return new SendableMessage(holders);
    }

    public static @NotNull SendableMessage of(@NotNull SendableHolder... holders) {
        return new SendableMessage(Arrays.asList(holders));
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<SendableHolder> holders = new ArrayList<>();

        private Builder() {
        }

        /**
         * Add holder to message.
         *
         * @param holders holders to add
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder addHolders(@NotNull SendableHolder... holders) {
            this.holders.addAll(Arrays.asList(holders));
            return this;
        }

        /**
         * Add chat message to the message.
         *
         * @param onlyConsole if true, message will be sent only to console
         * @param messages    messages to send
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder chat(boolean onlyConsole, @NotNull RawComponent... messages) {
            return this.addHolders(new ChatHolder(onlyConsole, messages));
        }

        /**
         * Add chat message to the message.
         *
         * @param messages messages to send
         * @return this builder
         */
        @Contract("_, -> this")
        public Builder chat(@NotNull RawComponent... messages) {
            return this.chat(false, messages);
        }

        /**
         * Parse raw chat message using MiniMessage and add it's to the message.<br>
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/">MiniMessage</a> to be in runtime.
         *
         * @param onlyConsole if true, message will be sent only to console
         * @param messages    messages to send
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder chat(boolean onlyConsole, @NotNull String... messages) {
            return this.chat(onlyConsole, MiniComponent.ofLegacy(messages));
        }

        /**
         * Parse raw chat message using MiniMessage and add it's to the message.<br>
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/">MiniMessage</a> to be in runtime.
         *
         * @param messages messages to send
         * @return this builder
         */
        @Contract("_, -> this")
        public Builder chat(@NotNull String... messages) {
            return this.chat(false, messages);
        }

        /**
         * Adds actionbar message to the message.
         *
         * @param message message to send
         * @return this builder
         */
        @Contract("_, -> this")
        public Builder actionBar(@NotNull RawComponent message) {
            return this.addHolders(new ActionBarHolder(message));
        }

        /**
         * Parse raw actionbar message using MiniMessage and add it's to the message.<br>
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/">MiniMessage</a> to be in runtime.
         *
         * @param message message to send
         * @return this builder
         */
        @Contract("_, -> this")
        public Builder actionBar(@NotNull String message) {
            return this.actionBar(MiniComponent.ofLegacy(message));
        }

        /**
         * Adds title message to the message
         *
         * @param title    title to send
         * @param subtitle subtitle to send
         * @param fadeIn   fade in time
         * @param stay     stay time
         * @param fadeOut  fade out time
         * @return this builder
         */
        @Contract("_, _, _, _, _ -> this")
        public Builder title(@NotNull RawComponent title, @NotNull RawComponent subtitle, int fadeIn, int stay, int fadeOut) {
            return this.addHolders(new TitleHolder(title, subtitle, TitleHolder.times(fadeIn, stay, fadeOut)));
        }

        /**
         * Parse raw title message using MiniMessage and add it's to the message.<br>
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/">MiniMessage</a> to be in runtime.
         *
         * @param title    title to send
         * @param subtitle subtitle to send
         * @param fadeIn   fade in time
         * @param stay     stay time
         * @param fadeOut  fade out time
         * @return this builder
         */
        @Contract("_, _, _, _, _ -> this")
        public Builder title(@NotNull String title, @NotNull String subtitle, int fadeIn, int stay, int fadeOut) {
            return this.title(MiniComponent.ofLegacy(title), MiniComponent.ofLegacy(subtitle), fadeIn, stay, fadeOut);
        }

        /**
         * Adds bossbar message to the message.
         *
         * @param name      bossbar name
         * @param progress  bossbar progress (0-1)
         * @param color     bossbar color
         * @param overlay   bossbar overlay
         * @param stay      bossbar stay time in ticks (if -1 won't be removed)
         * @param scheduler to remove bossbar after specific time (if null bossbar won't be removed)
         * @return this builder
         */
        @Contract("_, _, _, _, _, _, _ -> this")
        public Builder bossBar(@NotNull RawComponent name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Collection<BossBar.Flag> flags, int stay, @Nullable SchedulerWrapper scheduler) {
            return this.addHolders(BossBarHolder.builder(name)
                    .progress(progress)
                    .color(color)
                    .overlay(overlay)
                    .addFlags(flags)
                    .stay(scheduler, stay)
                    .build());
        }

        /**
         * Parse raw bossbar message using MiniMessage and add it's to the message.<br>
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/">MiniMessage</a> to be in runtime.
         *
         * @param name      bossbar name
         * @param progress  bossbar progress (0-1)
         * @param color     bossbar color
         * @param overlay   bossbar overlay
         * @param stay      bossbar stay time in ticks (if -1 won't be removed)
         * @param scheduler to remove bossbar after specific time (if null bossbar won't be removed)
         * @return this builder
         */
        @Contract("_, _, _, _, _, _, _ -> this")
        public Builder bossBar(@NotNull String name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Collection<BossBar.Flag> flags, int stay, @Nullable SchedulerWrapper scheduler) {
            return this.bossBar(MiniComponent.ofLegacy(name), progress, color, overlay, flags, stay, scheduler);
        }

        /**
         * Adds sound to the message.
         *
         * @param key             sound to play
         * @param volume          sound volume
         * @param pitch           sound pitch
         * @param stopOtherSounds if true, all other sounds will be stopped before playing this sound
         * @return this builder
         */
        @Contract("_, _, _, _, _ -> this")
        public Builder sound(@NotNull Key key, @NotNull Sound.Source source, float volume, float pitch, boolean stopOtherSounds) {
            return this.addHolders(new SoundHolder(Sound.sound(key, source, volume, pitch), stopOtherSounds));
        }

        /**
         * Adds sound to the message.
         *
         * @param key             sound to play
         * @param volume          sound volume
         * @param pitch           sound pitch
         * @param stopOtherSounds if true, all other sounds will be stopped before playing this sound
         * @return this builder
         */
        @Contract("_, _, _, _, _ -> this")
        public Builder sound(@NotNull String key, @NotNull Sound.Source source, float volume, float pitch, boolean stopOtherSounds) {
            return this.sound(Key.key(key), source, volume, pitch, stopOtherSounds);
        }

        /**
         * Adds sound to the message.
         *
         * @param key    sound to play
         * @param volume sound volume
         * @param pitch  sound pitch
         * @return this builder
         */
        @Contract("_, _, _, _ -> this")
        public Builder sound(@NotNull Key key, @NotNull Sound.Source source, float volume, float pitch) {
            return this.sound(key, source, volume, pitch, false);
        }

        /**
         * Adds sound to the message.
         *
         * @param key    sound to play
         * @param volume sound volume
         * @param pitch  sound pitch
         * @return this builder
         */
        @Contract("_, _, _, _ -> this")
        public Builder sound(@NotNull String key, @NotNull Sound.Source source, float volume, float pitch) {
            return this.sound(Key.key(key), source, volume, pitch, false);
        }

        /**
         * Build message with all added components.
         *
         * @return built message
         */
        @NotNull
        public SendableMessage build() {
            return new SendableMessage(this.holders);
        }

    }

}
