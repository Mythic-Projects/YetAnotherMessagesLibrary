package pl.peridot.yetanothermessageslibrary.message.holder.impl;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.replace.ComponentReplacer;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.util.adventure.RawComponent;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;

public class BossBarHolder extends SendableHolder {

    private final RawComponent name;
    private final float progress;
    private final BossBar.Color color;
    private final BossBar.Overlay overlay;
    private final int stay;

    private final SchedulerWrapper scheduler;

    public BossBarHolder(@NotNull RawComponent name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, int stay, @Nullable SchedulerWrapper scheduler) {
        this.name = name;
        this.progress = progress;
        this.color = color;
        this.overlay = overlay;
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

}
