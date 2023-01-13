package pl.peridot.yetanothermessageslibrary.adventure;

import java.util.function.Function;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface AudienceSupplier<R> {

    @NotNull Audience getAudience(@NotNull R receiver);

    boolean isConsole(@NotNull R receiver);

    static @NotNull<R extends Audience> AudienceSupplier<R> of(Function<R, Boolean> checkConsoleFunction) {
        return new AudienceSupplier<R>() {
            @Override
            public @NotNull Audience getAudience(@NotNull R receiver) {
                return receiver;
            }

            @Override
            public boolean isConsole(@NotNull R receiver) {
                return checkConsoleFunction.apply(receiver);
            }
        };
    }

}
