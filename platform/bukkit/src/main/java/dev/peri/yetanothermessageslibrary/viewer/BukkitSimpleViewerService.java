package dev.peri.yetanothermessageslibrary.viewer;

import java.util.UUID;
import java.util.function.BiConsumer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BukkitSimpleViewerService extends SimpleViewerService<CommandSender, UUID, SimpleViewer> {

    public BukkitSimpleViewerService(@NotNull JavaPlugin plugin, @NotNull BukkitAudiences adventure) {
        super(
                new BukkitViewerDataSupplier(adventure),
                (receiver, audience, console) -> new SimpleViewer(audience, console, prepareSchedulerConsumer(plugin))
        );
    }

    private static BiConsumer<Runnable, Long> prepareSchedulerConsumer(JavaPlugin plugin) {
        return (runnable, delay) -> plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

}
