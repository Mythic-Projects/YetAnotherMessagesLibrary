package pl.peridot.yetanothermessageslibrary;

import java.util.Locale;
import java.util.function.Function;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.message.Sendable;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;

public interface SendableMessageService<R, C extends MessageRepository> extends MessageService<C> {

    default void sendMessage(@Nullable R receiver, @NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier, @NotNull Replaceable... replacements) {
        if (receiver == null) {
            return;
        }

        Sendable message = this.supplyValue(receiver, messageSupplier);
        if (message == null) {
            return;
        }

        Locale locale = this.getLocale(receiver);
        Audience audience = this.getAudienceSupplier().getAudience(receiver);
        boolean console = this.getAudienceSupplier().isConsole(receiver);

        message.send(locale, audience, console, replacements);
    }

    @NotNull AudienceSupplier<R> getAudienceSupplier();

}
