package pl.peridot.yetanothermessageslibrary.viewer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.util.SchedulerWrapper;

public class SimpleViewerService<R, K, V extends SimpleViewer> implements ViewerService<R, V> {

    private final ViewerDataSupplier<R, K> viewerDataSupplier;
    private final ViewerFactory<R, V> viewerFactory;
    private final SchedulerWrapper schedulerWrapper;

    private final Map<K, V> viewers = new ConcurrentHashMap<>();

    public SimpleViewerService(
            @NotNull ViewerDataSupplier<R, K> viewerDataSupplier,
            @NotNull ViewerFactory<R, V> viewerFactory,
            @NotNull SchedulerWrapper schedulerWrapper
    ) {
        this.viewerDataSupplier = viewerDataSupplier;
        this.viewerFactory = viewerFactory;
        this.schedulerWrapper = schedulerWrapper;
    }

    public @NotNull V findOrCreateViewer(@NotNull R receiver) {
        Audience audience = this.viewerDataSupplier.getAudience(receiver);
        boolean console = this.viewerDataSupplier.isConsole(receiver);

        K key = this.viewerDataSupplier.getKey(receiver);
        if (key == null) {
            return this.viewerFactory.createViewer(receiver, audience, console, this::schedule);
        }

        return this.viewers.computeIfAbsent(
                key,
                k -> this.viewerFactory.createViewer(receiver, audience, console, this::schedule)
        );
    }

    @Override
    public @Nullable V removeViewer(@NotNull R receiver) {
        K key = this.viewerDataSupplier.getKey(receiver);
        if (key == null) {
            return null;
        }

        V remove = this.viewers.remove(key);
        if (remove != null) {
            remove.clearBossBars();
            remove.stopSounds();
        }
        return remove;
    }

    private void schedule(Runnable runnable, int delay) {
        this.schedulerWrapper.runTaskLater(runnable, delay);
    }


}
