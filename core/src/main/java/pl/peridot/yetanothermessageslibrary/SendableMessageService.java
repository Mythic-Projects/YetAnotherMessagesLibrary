package pl.peridot.yetanothermessageslibrary;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.message.MessageDispatcher;
import pl.peridot.yetanothermessageslibrary.message.Sendable;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;

public interface SendableMessageService<R, C extends MessageRepository> extends MessageService<C> {

    default MessageDispatcher<R> supplyMessage(@Nullable Object entity, @NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier) {
        return new MessageDispatcher<>(this.getAudienceSupplier(), this.getLocaleProvider(), this.supplyValue(entity, messageSupplier));
    }

    default void sendMessage(@Nullable R receiver, @NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier, @NotNull Replaceable... replacements) {
        this.supplyMessage(receiver, messageSupplier)
                .with(replacements)
                .sendTo(receiver);
    }

    @NotNull AudienceSupplier<R> getAudienceSupplier();

}
