package pl.peridot.yetanothermessageslibrary.message;

import java.util.Locale;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;

public class BukkitMessageDispatcher<D extends BukkitMessageDispatcher<D>> extends MessageDispatcher<CommandSender, D> {

    public BukkitMessageDispatcher(@NotNull AudienceSupplier<CommandSender> audienceSupplier, @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier, @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier) {
        super(audienceSupplier, localeSupplier, messageSupplier);
    }

    public D broadcast() {
        this.broadcastPlayers();
        this.console();
        return (D) this;
    }

    public D broadcastPlayers() {
        this.receivers(Bukkit.getOnlinePlayers());
        return (D) this;
    }

    public D console() {
        this.receiver(Bukkit.getConsoleSender());
        return (D) this;
    }

    public D permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (D) this;
    }

}
