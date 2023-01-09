package pl.peridot.yetanothermessageslibrary.adventure;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface AudienceSupplier<R> {

    @NotNull Audience getAudience(@NotNull R receiver);

    boolean isConsole(@NotNull R receiver);

}
