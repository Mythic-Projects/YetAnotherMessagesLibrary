package pl.peridot.yetanothermessageslibrary.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;

public class BukkitSchedulerWrapper implements SchedulerWrapper {

    private final JavaPlugin plugin;
    private final BukkitScheduler scheduler;

    public BukkitSchedulerWrapper(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    @Override
    public void runTask(@NotNull Runnable runnable, boolean async) {
        if (async) {
            this.scheduler.runTaskAsynchronously(this.plugin, runnable);
        } else {
            this.scheduler.runTask(this.plugin, runnable);
        }
    }

    @Override
    public void runTaskLater(@NotNull Runnable runnable, long delay, boolean async) {
        if (async) {
            this.scheduler.runTaskLaterAsynchronously(this.plugin, runnable, delay);
        } else {
            this.scheduler.runTaskLater(this.plugin, runnable, delay);
        }
    }

    @Override
    public void runTaskTimer(@NotNull Runnable runnable, long delay, long period, boolean async) {
        if (async) {
            this.scheduler.runTaskTimerAsynchronously(this.plugin, runnable, delay, period);
        } else {
            this.scheduler.runTaskTimer(this.plugin, runnable, delay, period);
        }
    }

}
