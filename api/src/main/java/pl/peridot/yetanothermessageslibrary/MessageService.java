package pl.peridot.yetanothermessageslibrary;

import java.util.List;
import java.util.function.Function;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.message.Sendable;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.replace.StringReplacer;

public interface MessageService<R, C extends MessageRepository> {

    default @Nullable <T> T supplyValue(@Nullable R receiver, @NotNull Function<@NotNull C, @Nullable T> valueSupplier) {
        return valueSupplier.apply(this.getMessageRepository(receiver));
    }

    default @Nullable <T> T supplyValue(@NotNull Function<@NotNull C, @Nullable T> valueSupplier) {
        return this.supplyValue(null, valueSupplier);
    }

    @Contract(pure = true)
    default @Nullable String supplyString(@Nullable R receiver, @NotNull Function<@NotNull C, @Nullable String> stringSupplier, @NotNull Replaceable... replacements) {
        String string = this.supplyValue(receiver, stringSupplier);
        if (string == null) {
            return null;
        }
        return StringReplacer.replace(string, replacements);
    }

    default @Nullable List<String> supplyStringList(@Nullable R receiver, @NotNull Function<@NotNull C, @Nullable List<String>> stringSupplier, @NotNull Replaceable... replacements) {
        List<String> stringList = this.supplyValue(receiver, stringSupplier);
        if (stringList == null || stringList.isEmpty()) {
            return stringList;
        }
        return StringReplacer.replace(stringList, replacements);
    }

    default void sendMessage(@Nullable R receiver, @NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier, @NotNull Replaceable... replacements) {
        if (receiver == null) {
            return;
        }

        Sendable message = this.supplyValue(receiver, messageSupplier);
        if (message == null) {
            return;
        }

        Audience audience = this.getAudienceSupplier().getAudience(receiver);
        boolean console = this.getAudienceSupplier().isConsole(receiver);

        message.send(audience, console, replacements);
    }

    @NotNull AudienceSupplier<R> getAudienceSupplier();

    @NotNull C getMessageRepository(R receiver);

}
