package pl.peridot.yetanothermessageslibrary.util;

import com.velocitypowered.api.scheduler.Scheduler;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public class VelocitySchedulerWrapper implements SchedulerWrapper {

    private final Object plugin;
    private final Scheduler scheduler;

    public VelocitySchedulerWrapper(Object plugin, Scheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
    }

    @Override
    public void runTask(@NotNull Runnable runnable, boolean async) {
        this.scheduler.buildTask(this.plugin, runnable).schedule();
    }

    @Override
    public void runTaskLater(@NotNull Runnable runnable, long delay, boolean async) {
        this.scheduler.buildTask(this.plugin, runnable)
                .delay(SchedulerWrapper.toMillis(delay), TimeUnit.MILLISECONDS)
                .schedule();
    }

    @Override
    public void runTaskTimer(@NotNull Runnable runnable, long delay, long period, boolean async) {
        this.scheduler.buildTask(this.plugin, runnable)
                .delay(SchedulerWrapper.toMillis(delay), TimeUnit.MILLISECONDS)
                .repeat(SchedulerWrapper.toMillis(period), TimeUnit.MILLISECONDS)
                .schedule();
    }

}
