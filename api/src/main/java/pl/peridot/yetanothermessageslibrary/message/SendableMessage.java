package pl.peridot.yetanothermessageslibrary.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.ChatHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.SoundHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.TitleHolder;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.util.adventure.MiniComponent;
import pl.peridot.yetanothermessageslibrary.util.adventure.RawComponent;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;

public class SendableMessage implements Sendable {

    private final List<SendableHolder> holders = new ArrayList<>();

    public SendableMessage(Collection<SendableHolder> holders) {
        this.holders.addAll(holders);
    }

    public SendableMessage(SendableHolder... holders) {
        this(Arrays.asList(holders));
    }

    public @NotNull List<SendableHolder> getHolders() {
        return new ArrayList<>(this.holders);
    }

    public void send(@NotNull Audience audience, @NotNull Replaceable... replacements) {
        this.holders.forEach(holder -> holder.send(audience, replacements));
    }

    public void send(@NotNull Audience audience, boolean console, Replaceable... replacements) {
        this.holders.forEach(holder -> holder.send(audience, console, replacements));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<ChatHolder> chatHolders = new ArrayList<>();
        private final List<BossBarHolder> bossBarHolders = new ArrayList<>();
        private final List<SoundHolder> soundHolders = new ArrayList<>();
        private ActionBarHolder actionBarHolder;
        private TitleHolder titleHolder;

        public Builder() {
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
            this.chatHolders.add(new ChatHolder(onlyConsole, messages));
            return this;
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
         * Parse raw chat message using MiniMessage and add it's to the message
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/>MiniMessage</a> to be in runtime.
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
         * Parse raw chat message using MiniMessage and add it's to the message
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/>MiniMessage</a> to be in runtime.
         *
         * @param messages messages to send
         * @return this builder
         */
        @Contract("_, -> this")
        public Builder chat(@NotNull String... messages) {
            return this.chat(false, messages);
        }

        /**
         * Adds actionbar message to the message
         *
         * @param message message to send
         * @return
         */
        @Contract("_, -> this")
        public Builder actionBar(@NotNull RawComponent message) {
            this.actionBarHolder = new ActionBarHolder(message);
            return this;
        }

        /**
         * Parse raw actionbar message using MiniMessage and add it's to the message
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/>MiniMessage</a> to be in runtime.
         *
         * @param message message to send
         * @return this builder
         */
        @Contract("_, -> this")
        public Builder actionBar(@NotNull String message) {
            return this.actionBar(MiniComponent.ofLegacy(message));
        }

        @Contract("_, _, _, _, _ -> this")
        public Builder title(RawComponent title, RawComponent subtitle, int fadeIn, int stay, int fadeOut) {
            this.titleHolder = new TitleHolder(title, subtitle, fadeIn, stay, fadeOut);
            return this;
        }

        @Contract("_, _, _, _, _ -> this")
        public Builder title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
            return this.title(MiniComponent.ofLegacy(title), MiniComponent.ofLegacy(subtitle), fadeIn, stay, fadeOut);
        }

        /**
         * Adds bossbar message to the message
         *
         * @param name      bossbar name
         * @param percent   bossbar percent
         * @param color     bossbar color
         * @param overlay   bossbar overlay
         * @param stay      bossbar stay time in ticks (if -1 won't be removed)
         * @param scheduler to remove bossbar after specific time (if null bossbar won't be removed)
         * @return this builder
         */
        @Contract("_, _, _, _, _, _ -> this")
        public Builder bossBar(@NotNull RawComponent name, int percent, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, int stay, @Nullable SchedulerWrapper scheduler) {
            this.bossBarHolders.add(new BossBarHolder(name, percent, color, overlay, stay, scheduler));
            return this;
        }

        /**
         * Parse raw bossbar message using MiniMessage and add it's to the message
         * Requires <a href="https://docs.adventure.kyori.net/minimessage/>MiniMessage</a> to be in runtime.
         *
         * @param name      bossbar name
         * @param percent   bossbar percent
         * @param color     bossbar color
         * @param overlay   bossbar overlay
         * @param stay      bossbar stay time in ticks (if -1 won't be removed)
         * @param scheduler to remove bossbar after specific time (if null bossbar won't be removed)
         * @return this builder
         */
        @Contract("_, _, _, _, _, _ -> this")
        public Builder bossBar(@NotNull String name, int percent, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, int stay, @Nullable SchedulerWrapper scheduler) {
            return this.bossBar(MiniComponent.ofLegacy(name), percent, color, overlay, stay, scheduler);
        }

        /**
         * Adds sound to the message
         *
         * @param key             sound to play
         * @param volume          sound volume
         * @param pitch           sound pitch
         * @param stopOtherSounds if true, all other sounds will be stopped before playing this sound
         * @return this builder
         */
        @Contract("_, _, _, _, _ -> this")
        public Builder sound(@NotNull Key key, @NotNull Sound.Source source, float volume, float pitch, boolean stopOtherSounds) {
            this.soundHolders.add(new SoundHolder(key, source, volume, pitch, stopOtherSounds));
            return this;
        }

        /**
         * Adds sound to the message
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
         * Adds sound to the message
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
         * Adds sound to the message
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

        @NotNull
        public SendableMessage build() {
            List<SendableHolder> holders = new ArrayList<>();

            holders.addAll(this.chatHolders);
            holders.addAll(this.bossBarHolders);
            holders.addAll(this.soundHolders);

            if (this.actionBarHolder != null) {
                holders.add(this.actionBarHolder);
            }

            if (this.titleHolder != null) {
                holders.add(this.titleHolder);
            }

            return new SendableMessage(holders);
        }

    }

}