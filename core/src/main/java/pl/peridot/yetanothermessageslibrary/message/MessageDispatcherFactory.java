package pl.peridot.yetanothermessageslibrary.message;

import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;

public interface MessageDispatcherFactory<R, D extends MessageDispatcher<?, D>> {

    @NotNull D prepareDispatcher(@NotNull AudienceSupplier<R> audienceSupplier, @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier, @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier);

}
