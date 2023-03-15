package dev.peri.yetanothermessageslibrary.viewer;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BungeeViewerService extends ViewerService<CommandSender> {

    public BungeeViewerService(@NotNull Plugin plugin, @NotNull BungeeAudiences adventure) {
        super(
                new BungeeViewerDataSupplier(adventure),
                (receiver, audience, console) -> new Viewer(audience, console, wrapScheduler(plugin))
        );
    }

    private static BiConsumer<Runnable, Long> wrapScheduler(Plugin plugin) {
        return (runnable, delay) -> plugin.getProxy().getScheduler().schedule(plugin, runnable, delay * 50L, TimeUnit.MILLISECONDS);
    }

}
