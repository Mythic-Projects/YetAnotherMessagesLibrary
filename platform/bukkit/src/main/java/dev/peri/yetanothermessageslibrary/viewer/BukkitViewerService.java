package dev.peri.yetanothermessageslibrary.viewer;

import java.util.function.BiConsumer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BukkitViewerService extends ViewerService<CommandSender> {

    public BukkitViewerService(@NotNull JavaPlugin plugin, @NotNull BukkitAudiences adventure) {
        super(
                new BukkitViewerDataSupplier(adventure),
                (receiver, audience, console) -> new Viewer(audience, console, wrapScheduler(plugin))
        );
    }

    private static BiConsumer<Runnable, Long> wrapScheduler(JavaPlugin plugin) {
        return (runnable, delay) -> plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

}
