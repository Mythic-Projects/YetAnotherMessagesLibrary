package pl.peridot.yetanothermessageslibrary.util;

import org.jetbrains.annotations.NotNull;

public interface SchedulerWrapper {

    void runTask(@NotNull Runnable runnable, boolean async);

    default void runTask(@NotNull Runnable runnable) {
        this.runTask(runnable, false);
    }

    void runTaskLater(@NotNull Runnable runnable, long delay, boolean async);

    default void runTaskLater(@NotNull Runnable runnable, long delay) {
        this.runTaskLater(runnable, delay, false);
    }

    void runTaskTimer(@NotNull Runnable runnable, long delay, long period, boolean async);

    default void runTaskTimer(@NotNull Runnable runnable, long delay, long period) {
        this.runTaskTimer(runnable, delay, period, false);
    }

    static long toMillis(long ticks) {
        return ticks * 50;
    }

}
