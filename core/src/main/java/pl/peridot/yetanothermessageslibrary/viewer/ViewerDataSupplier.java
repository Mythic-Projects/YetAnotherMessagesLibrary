package pl.peridot.yetanothermessageslibrary.viewer;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewerDataSupplier<R, K> {

    @NotNull Audience getAudience(@NotNull R receiver);

    boolean isConsole(@NotNull R receiver);

    @Nullable K getKey(@NotNull R receiver);

}
