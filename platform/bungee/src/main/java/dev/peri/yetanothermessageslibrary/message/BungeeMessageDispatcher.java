package dev.peri.yetanothermessageslibrary.message;

import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.Locale;
import java.util.function.Function;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BungeeMessageDispatcher<D extends BungeeMessageDispatcher<?>> extends MessageDispatcher<CommandSender, D> {

    public BungeeMessageDispatcher(
            @NotNull ViewerService<CommandSender> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    public D broadcast() {
        this.broadcastPlayers();
        this.console();
        return (D) this;
    }

    public D broadcastPlayers() {
        this.receivers(ProxyServer.getInstance().getPlayers());
        return (D) this;
    }

    public D console() {
        this.receiver(ProxyServer.getInstance().getConsole());
        return (D) this;
    }

    public D permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (D) this;
    }

}
