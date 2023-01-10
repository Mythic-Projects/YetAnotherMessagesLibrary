package pl.peridot.yetanothermessageslibrary.adventure;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

public class BungeeAudienceSupplier implements AudienceSupplier<CommandSender> {

    private final BungeeAudiences adventure;

    public BungeeAudienceSupplier(BungeeAudiences adventure) {
        this.adventure = adventure;
    }

    @Override
    public @NotNull Audience getAudience(@NotNull CommandSender receiver) {
        return this.adventure.sender(receiver);
    }

    @Override
    public boolean isConsole(@NotNull CommandSender receiver) {
        return !(receiver instanceof ProxiedPlayer);
    }

}
