package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.MiniComponent;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;
import pl.peridot.yetanothermessageslibrary.message.SendableMessage;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.replace.ComponentReplacer;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;

public class BossBarHolder extends SendableHolder {

    private final RawComponent name;
    private final float progress;
    private final BossBar.Color color;
    private final BossBar.Overlay overlay;
    private final Set<BossBar.Flag> flags = new HashSet<>();
    private final int stay;

    private final SchedulerWrapper scheduler;

    public BossBarHolder(@NotNull RawComponent name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Collection<BossBar.Flag> flags, int stay, @Nullable SchedulerWrapper scheduler) {
        this.name = name;
        this.progress = progress;
        this.color = color;
        this.overlay = overlay;
        this.flags.addAll(flags);
        this.stay = stay;
        this.scheduler = scheduler;
    }

    public @NotNull RawComponent getName() {
        return this.name;
    }

    public float getProgress() {
        return this.progress;
    }

    public @NotNull BossBar.Color getColor() {
        return this.color;
    }

    public @NotNull BossBar.Overlay getOverlay() {
        return this.overlay;
    }

    public @NotNull Set<BossBar.Flag> getFlags() {
        return this.flags;
    }

    public int getStay() {
        return this.stay;
    }

    public @NotNull BossBar prepareBossBar(@NotNull Replaceable... replacements) {
        return BossBar.bossBar(ComponentReplacer.replace(this.name, replacements), this.progress, this.color, this.overlay);
    }

    @Override
    public void send(@NotNull Audience audience, @NotNull Replaceable... replacements) {
        BossBar bossBar = this.prepareBossBar(replacements);
        audience.showBossBar(bossBar);
        if (this.scheduler != null && this.getStay() > 0) {
            this.scheduler.runTaskLater(() -> audience.hideBossBar(bossBar), this.getStay(), false);
        }
    }

    public static @NotNull SendableMessage message(@NotNull RawComponent name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Collection<BossBar.Flag> flags, int stay, @Nullable SchedulerWrapper scheduler) {
        return SendableMessage.of(new BossBarHolder(name, progress, color, overlay, flags, stay, scheduler));
    }

    public static @NotNull SendableMessage message(@NotNull String name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Collection<BossBar.Flag> flags, int stay, @Nullable SchedulerWrapper scheduler) {
        return SendableMessage.of(new BossBarHolder(MiniComponent.of(name), progress, color, overlay, flags, stay, scheduler));
    }

    public static @NotNull Builder builder(@NotNull RawComponent name) {
        return new Builder(name);
    }

    public static @NotNull Builder builder(@NotNull String name) {
        return new Builder(MiniComponent.ofLegacy(name));
    }

    public static class Builder {

        private final RawComponent name;

        private float progress = BossBar.MAX_PROGRESS;
        private BossBar.Color color = BossBar.Color.PINK;
        private BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;
        private final Set<BossBar.Flag> flags = new HashSet<>();

        private int stay = -1;
        private SchedulerWrapper scheduler;

        private Builder(@NotNull RawComponent name) {
            this.name = name;
        }

        @Contract("_ -> this")
        public Builder progress(float progress) {
            if (progress < BossBar.MIN_PROGRESS || progress > BossBar.MAX_PROGRESS) {
                throw new IllegalArgumentException("progress must be between " + BossBar.MIN_PROGRESS + " and " + BossBar.MAX_PROGRESS + ", was " + progress);
            }
            this.progress = progress;
            return this;
        }

        @Contract("_ -> this")
        public Builder color(@NotNull BossBar.Color color) {
            this.color = color;
            return this;
        }

        @Contract("_ -> this")
        public Builder overlay(@NotNull BossBar.Overlay overlay) {
            this.overlay = overlay;
            return this;
        }

        @Contract("_ -> this")
        public Builder addFlag(@NotNull BossBar.Flag flag) {
            this.flags.add(flag);
            return this;
        }

        @Contract("_, _ -> this")
        public Builder stay(@NotNull SchedulerWrapper scheduler, int stay) {
            this.scheduler = scheduler;
            this.stay = stay;
            return this;
        }

        public @NotNull BossBarHolder build() {
            return new BossBarHolder(this.name, this.progress, this.color, this.overlay, this.flags, this.stay, this.scheduler);
        }

    }

}
