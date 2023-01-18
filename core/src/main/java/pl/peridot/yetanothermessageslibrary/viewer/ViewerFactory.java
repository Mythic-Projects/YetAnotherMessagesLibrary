package pl.peridot.yetanothermessageslibrary.viewer;

import java.util.function.BiConsumer;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface ViewerFactory<R, V extends Viewer> {

    @NotNull V createViewer(@NotNull R receiver, @NotNull Audience audience, boolean console, BiConsumer<Runnable, Integer> schedule);

}
