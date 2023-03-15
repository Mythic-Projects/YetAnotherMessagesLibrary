package dev.peri.yetanothermessageslibrary;

import dev.peri.yetanothermessageslibrary.message.MessageDispatcher;
import dev.peri.yetanothermessageslibrary.message.MessageDispatcherFactory;
import dev.peri.yetanothermessageslibrary.viewer.ViewerDataSupplier;
import dev.peri.yetanothermessageslibrary.viewer.ViewerFactory;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleSendableMessageService<R, C extends MessageRepository, D extends MessageDispatcher<R, ? extends D>> extends SimpleMessageService<C> implements SendableMessageService<R, C, D> {

    private final ViewerService<R> viewerService;
    private final MessageDispatcherFactory<R, ? extends D> dispatcherFactory;

    public SimpleSendableMessageService(
            @NotNull ViewerService<R> viewerService,
            @NotNull MessageDispatcherFactory<R, ? extends D> dispatcherFactory
    ) {
        this.viewerService = viewerService;
        this.dispatcherFactory = dispatcherFactory;
    }

    public SimpleSendableMessageService(
            @NotNull ViewerDataSupplier<R> viewerDataSupplier,
            @NotNull ViewerFactory<R> viewerFactory,
            @NotNull MessageDispatcherFactory<R, ? extends D> dispatcherFactory
    ) {
        this(
                new ViewerService<>(viewerDataSupplier, viewerFactory),
                dispatcherFactory
        );
    }

    @Override
    public @NotNull ViewerService<R> getViewerService() {
        return this.viewerService;
    }

    @Override
    public @NotNull MessageDispatcherFactory<R, ? extends D> getDispatcherFactory() {
        return this.dispatcherFactory;
    }

}
