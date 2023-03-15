package dev.peri.yetanothermessageslibrary.message;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VelocityMessageDispatcher<D extends VelocityMessageDispatcher<?>> extends MessageDispatcher<CommandSource, D> {

    public VelocityMessageDispatcher(
            @NotNull ViewerService<CommandSource> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    public D broadcast(@NotNull ProxyServer proxyServer) {
        this.broadcastPlayers(proxyServer);
        this.console(proxyServer);
        return (D) this;
    }

    public D broadcastPlayers(@NotNull ProxyServer proxyServer) {
        proxyServer.getAllPlayers().forEach(this::receiver);
        return (D) this;
    }

    public D console(@NotNull ProxyServer proxyServer) {
        this.receiver(proxyServer.getConsoleCommandSource());
        return (D) this;
    }

    public D permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (D) this;
    }

}
