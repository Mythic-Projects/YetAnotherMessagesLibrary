package pl.peridot.yetanothermessageslibrary.viewer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleViewerService<R, K, V extends SimpleViewer> implements ViewerService<R, V> {

    private final ViewerDataSupplier<R, K> viewerDataSupplier;
    private final ViewerFactory<R, V> viewerFactory;

    private final Map<K, V> viewers = new ConcurrentHashMap<>();

    public SimpleViewerService(
            @NotNull ViewerDataSupplier<R, K> viewerDataSupplier,
            @NotNull ViewerFactory<R, V> viewerFactory
    ) {
        this.viewerDataSupplier = viewerDataSupplier;
        this.viewerFactory = viewerFactory;
    }

    public @NotNull V findOrCreateViewer(@NotNull R receiver) {
        Audience audience = this.viewerDataSupplier.getAudience(receiver);
        boolean console = this.viewerDataSupplier.isConsole(receiver);

        K key = this.viewerDataSupplier.getKey(receiver);
        if (key == null) {
            return this.viewerFactory.createViewer(receiver, audience, console);
        }

        return this.viewers.computeIfAbsent(
                key,
                k -> this.viewerFactory.createViewer(receiver, audience, console)
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

}
