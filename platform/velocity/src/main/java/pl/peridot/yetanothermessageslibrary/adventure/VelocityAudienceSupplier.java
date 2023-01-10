package pl.peridot.yetanothermessageslibrary.adventure;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public class VelocityAudienceSupplier implements AudienceSupplier<CommandSource> {

    @Override
    public @NotNull Audience getAudience(@NotNull CommandSource receiver) {
        return receiver;
    }

    @Override
    public boolean isConsole(@NotNull CommandSource receiver) {
        return !(receiver instanceof Player);
    }

}
