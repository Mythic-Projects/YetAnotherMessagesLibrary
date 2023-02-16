package dev.peri.yetanothermessageslibrary;

import dev.peri.yetanothermessageslibrary.message.MessageDispatcher;
import dev.peri.yetanothermessageslibrary.message.MessageDispatcherFactory;
import dev.peri.yetanothermessageslibrary.message.Sendable;
import dev.peri.yetanothermessageslibrary.replace.Replaceable;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SendableMessageService<R, C extends MessageRepository, D extends MessageDispatcher<R, D>> extends MessageService<C> {

    /**
     * Wrap message in {@link MessageDispatcher} to easily send it
     *
     * @param messageSupplier function to get message from repository
     * @return MessageDispatcher
     */
    default D getMessage(@NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier) {
        return this.getDispatcherFactory().prepareDispatcher(this.getViewerService(), this::getLocale, (entity) -> this.get(entity, messageSupplier));
    }

    @Deprecated
    default void sendMessage(@Nullable R receiver, @NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier, @NotNull Replaceable... replacements) {
        this.getMessage(messageSupplier)
                .with(replacements)
                .sendTo(receiver);
    }

    @NotNull ViewerService<R, ? extends Viewer> getViewerService();

    @NotNull MessageDispatcherFactory<R, D> getDispatcherFactory();

}
