package pl.peridot.yetanothermessageslibrary.adventure;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BukkitAudienceSupplier implements AudienceSupplier<CommandSender> {

    private final BukkitAudiences adventure;

    public BukkitAudienceSupplier(BukkitAudiences adventure) {
        this.adventure = adventure;
    }

    @Override
    public @NotNull Audience getAudience(@NotNull CommandSender receiver) {
        return this.adventure.sender(receiver);
    }

    @Override
    public boolean isConsole(@NotNull CommandSender receiver) {
        return !(receiver instanceof Player);
    }

}
