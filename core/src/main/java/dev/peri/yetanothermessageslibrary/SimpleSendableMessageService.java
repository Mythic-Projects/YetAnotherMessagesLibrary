package dev.peri.yetanothermessageslibrary;

import dev.peri.yetanothermessageslibrary.message.MessageDispatcher;
import dev.peri.yetanothermessageslibrary.message.MessageDispatcherFactory;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleSendableMessageService<R, C extends MessageRepository, D extends MessageDispatcher<R, D>> extends SimpleMessageService<C> implements SendableMessageService<R, C, D> {

    private final ViewerService<R, ? extends Viewer> viewerService;
    private final MessageDispatcherFactory<R, D> dispatcherFactory;

    public SimpleSendableMessageService(
            @NotNull ViewerService<R, ? extends Viewer> viewerService,
            @NotNull MessageDispatcherFactory<R, D> dispatcherFactory
    ) {
        this.viewerService = viewerService;
        this.dispatcherFactory = dispatcherFactory;
    }

    @Override
    public @NotNull ViewerService<R, ? extends Viewer> getViewerService() {
        return this.viewerService;
    }

    @Override
    public @NotNull MessageDispatcherFactory<R, D> getDispatcherFactory() {
        return this.dispatcherFactory;
    }

}
