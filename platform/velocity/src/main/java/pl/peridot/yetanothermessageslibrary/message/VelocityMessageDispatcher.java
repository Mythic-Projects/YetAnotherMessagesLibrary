package pl.peridot.yetanothermessageslibrary.message;

import com.velocitypowered.api.command.CommandSource;
import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.viewer.Viewer;
import pl.peridot.yetanothermessageslibrary.viewer.ViewerService;

public class VelocityMessageDispatcher<D extends VelocityMessageDispatcher<D>> extends MessageDispatcher<CommandSource, D> {

    public VelocityMessageDispatcher(
            @NotNull ViewerService<CommandSource, ? extends Viewer> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    public D permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (D) this;
    }

}
