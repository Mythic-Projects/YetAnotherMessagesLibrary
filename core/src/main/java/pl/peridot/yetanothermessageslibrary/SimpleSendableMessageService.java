package pl.peridot.yetanothermessageslibrary;

import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.message.MessageDispatcher;
import pl.peridot.yetanothermessageslibrary.message.MessageDispatcherFactory;
import pl.peridot.yetanothermessageslibrary.viewer.Viewer;
import pl.peridot.yetanothermessageslibrary.viewer.ViewerService;

public abstract class SimpleSendableMessageService<R, C extends MessageRepository, D extends MessageDispatcher<R, D>> extends SimpleMessageService<C> implements SendableMessageService<R, C, D> {

    private final ViewerService<R, Viewer> viewerService;
    private final MessageDispatcherFactory<R, D> dispatcherFactory;

    public SimpleSendableMessageService(
            @NotNull ViewerService<R, Viewer> viewerService,
            @NotNull MessageDispatcherFactory<R, D> dispatcherFactory
    ) {
        this.viewerService = viewerService;
        this.dispatcherFactory = dispatcherFactory;
    }

    @Override
    public @NotNull ViewerService<R, Viewer> getViewerService() {
        return this.viewerService;
    }

    @Override
    public @NotNull MessageDispatcherFactory<R, D> getDispatcherFactory() {
        return this.dispatcherFactory;
    }

}
