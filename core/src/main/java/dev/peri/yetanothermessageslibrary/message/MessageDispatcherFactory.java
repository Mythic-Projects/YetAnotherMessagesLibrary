package dev.peri.yetanothermessageslibrary.message;

import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MessageDispatcherFactory<R, D extends MessageDispatcher<?, D>> {

    @NotNull D prepareDispatcher(
            @NotNull ViewerService<R, ? extends Viewer> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    );

}
