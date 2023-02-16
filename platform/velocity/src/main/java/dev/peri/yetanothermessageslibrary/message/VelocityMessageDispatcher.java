package dev.peri.yetanothermessageslibrary.message;

import com.velocitypowered.api.command.CommandSource;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
