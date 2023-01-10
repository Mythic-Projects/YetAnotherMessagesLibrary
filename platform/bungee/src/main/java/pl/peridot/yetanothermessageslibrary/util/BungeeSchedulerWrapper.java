package pl.peridot.yetanothermessageslibrary.util;

import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;

public class BungeeSchedulerWrapper implements SchedulerWrapper {

    private final Plugin plugin;
    private final TaskScheduler scheduler;

    public BungeeSchedulerWrapper(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getProxy().getScheduler();
    }

    @Override
    public void runTask(@NotNull Runnable runnable, boolean async) {
        this.scheduler.runAsync(this.plugin, runnable);
    }

    @Override
    public void runTaskLater(@NotNull Runnable runnable, long delay, boolean async) {
        this.scheduler.schedule(this.plugin, runnable, SchedulerWrapper.toMillis(delay), TimeUnit.MILLISECONDS);
    }

    @Override
    public void runTaskTimer(@NotNull Runnable runnable, long delay, long period, boolean async) {
        this.scheduler.schedule(this.plugin, runnable, SchedulerWrapper.toMillis(delay), SchedulerWrapper.toMillis(period), TimeUnit.MILLISECONDS);
    }

}
