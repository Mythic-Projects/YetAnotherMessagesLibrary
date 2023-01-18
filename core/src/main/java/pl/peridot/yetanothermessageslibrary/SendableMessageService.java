package pl.peridot.yetanothermessageslibrary;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.message.MessageDispatcher;
import pl.peridot.yetanothermessageslibrary.message.MessageDispatcherFactory;
import pl.peridot.yetanothermessageslibrary.message.Sendable;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.viewer.Viewer;
import pl.peridot.yetanothermessageslibrary.viewer.ViewerService;

public interface SendableMessageService<R, C extends MessageRepository, D extends MessageDispatcher<R, D>> extends MessageService<C> {

    default D getMessage(@NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier) {
        return this.getDispatcherFactory().prepareDispatcher(this.getViewerService(), this::getLocale, (entity) -> this.get(entity, messageSupplier));
    }

    default void sendMessage(@Nullable R receiver, @NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier, @NotNull Replaceable... replacements) {
        this.getMessage(messageSupplier)
                .with(replacements)
                .sendTo(receiver);
    }

    @NotNull ViewerService<R, Viewer> getViewerService();

    @NotNull MessageDispatcherFactory<R, D> getDispatcherFactory();

}
