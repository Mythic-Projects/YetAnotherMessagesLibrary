package pl.peridot.yetanothermessageslibrary.viewer;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

public class SimpleViewer implements Viewer {

    private final Audience audience;
    private final boolean console;
    private final Set<BossBar> bossBars = ConcurrentHashMap.newKeySet();
    private final BiConsumer<Runnable, Integer> schedule;

    public SimpleViewer(Audience audience, boolean console, BiConsumer<Runnable, Integer> schedule) {
        this.audience = audience;
        this.console = console;
        this.schedule = schedule;
    }

    public Audience getAudience() {
        return this.audience;
    }

    @Override
    public boolean isConsole() {
        return this.console;
    }

    @Override
    public void sendChatMessage(@NotNull Collection<Component> messages) {
        messages.forEach(this.audience::sendMessage);
    }

    @Override
    public void sendActionBar(@NotNull Component message) {
        this.audience.sendActionBar(message);
    }

    @Override
    public void sendTitle(@NotNull Title title) {
        this.audience.showTitle(title);
    }

    @Override
    public void sendBossBar(@NotNull BossBar bossBar, int stay) {
        this.audience.showBossBar(bossBar);
        this.bossBars.add(bossBar);

        if (stay < 0) {
            return;
        }

        this.schedule.accept(() -> {
            this.audience.hideBossBar(bossBar);
            this.bossBars.remove(bossBar);
        }, stay);
    }

    @Override
    public void clearBossBars() {
        this.bossBars.forEach(this.audience::hideBossBar);
        this.bossBars.clear();
    }

    @Override
    public void sendSound(@NotNull Sound sound) {
        this.audience.playSound(sound);
    }

    @Override
    public void stopSounds() {
        this.audience.stopSound(SoundStop.all());
    }

}
