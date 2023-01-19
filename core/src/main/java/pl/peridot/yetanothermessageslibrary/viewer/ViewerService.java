package pl.peridot.yetanothermessageslibrary.viewer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewerService<R, V extends Viewer> {

    @NotNull V findOrCreateViewer(@NotNull R receiver);

    @Nullable V removeViewer(@NotNull R receiver);

}
