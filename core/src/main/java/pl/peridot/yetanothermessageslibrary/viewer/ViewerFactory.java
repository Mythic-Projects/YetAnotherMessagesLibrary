package pl.peridot.yetanothermessageslibrary.viewer;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface ViewerFactory<R, V extends Viewer> {

    @NotNull V createViewer(@NotNull R receiver, @NotNull Audience audience, boolean console);

}
