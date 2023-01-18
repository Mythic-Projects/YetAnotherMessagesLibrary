package pl.peridot.yetanothermessageslibrary.viewer;

import java.util.Collection;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

public interface Viewer {

    boolean isConsole();

    void sendChatMessage(@NotNull Collection<Component> messages);

    void sendActionBar(@NotNull Component message);

    void sendTitle(@NotNull Title title);

    void sendBossBar(@NotNull BossBar bossBar, int stay);

    void clearBossBars();

    void sendSound(@NotNull Sound sound);

    void stopSounds();

}
