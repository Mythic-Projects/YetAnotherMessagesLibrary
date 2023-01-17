package pl.peridot.yetanothermessageslibrary;

import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.message.MessageDispatcher;
import pl.peridot.yetanothermessageslibrary.message.MessageDispatcherFactory;

public abstract class SimpleSendableMessageService<R, C extends MessageRepository, D extends MessageDispatcher<R, D>> extends SimpleMessageService<C> implements SendableMessageService<R, C, D> {

    private final AudienceSupplier<R> audienceSupplier;
    private final MessageDispatcherFactory<R, D> dispatcherFactory;

    public SimpleSendableMessageService(@NotNull AudienceSupplier<R> audienceSupplier, MessageDispatcherFactory<R, D> dispatcherFactory) {
        this.audienceSupplier = audienceSupplier;
        this.dispatcherFactory = dispatcherFactory;
    }

    @Override
    public @NotNull AudienceSupplier<R> getAudienceSupplier() {
        return this.audienceSupplier;
    }

    @Override
    public @NotNull MessageDispatcherFactory<R, D> getDispatcherFactory() {
        return this.dispatcherFactory;
    }
}
