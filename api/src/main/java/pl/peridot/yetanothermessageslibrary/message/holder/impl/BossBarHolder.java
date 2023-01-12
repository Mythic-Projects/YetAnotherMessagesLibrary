package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.MiniComponent;
import pl.peridot.yetanothermessageslibrary.message.SendableMessage;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.replace.ComponentReplacer;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.adventure.RawComponent;
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

}
