package pl.peridot.yetanothermessageslibrary.viewer;

import org.jetbrains.annotations.NotNull;

public interface ViewerService<R, V extends Viewer> {

    @NotNull V findOrCreateViewer(@NotNull R receiver);

    void removeViewer(@NotNull R receiver);

}
