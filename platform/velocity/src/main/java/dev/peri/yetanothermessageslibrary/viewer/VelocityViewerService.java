package dev.peri.yetanothermessageslibrary.viewer;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

public class VelocityViewerService extends ViewerService<CommandSource> {

    public VelocityViewerService(@NotNull ProxyServer proxyServer, @NotNull Object plugin) {
        super(
                new VelocityViewerDataSupplier(),
                (receiver, audience, console) -> new Viewer(audience, console, wrapScheduler(proxyServer, plugin))
        );
    }

    private static BiConsumer<Runnable, Long> wrapScheduler(ProxyServer proxyServer, Object plugin) {
        return (runnable, delay) -> proxyServer.getScheduler()
                .buildTask(plugin, runnable)
                .delay(delay * 50L, TimeUnit.MILLISECONDS)
                .schedule();
    }

}
