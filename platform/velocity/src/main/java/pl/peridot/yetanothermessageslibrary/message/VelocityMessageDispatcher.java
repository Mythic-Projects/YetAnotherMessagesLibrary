package pl.peridot.yetanothermessageslibrary.message;

import com.velocitypowered.api.command.CommandSource;
import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;

public class VelocityMessageDispatcher<D extends VelocityMessageDispatcher<D>> extends MessageDispatcher<CommandSource, D> {

    public VelocityMessageDispatcher(@NotNull AudienceSupplier<CommandSource> audienceSupplier, @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier, @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier) {
        super(audienceSupplier, localeSupplier, messageSupplier);
    }

    public D permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (D) this;
    }

}
