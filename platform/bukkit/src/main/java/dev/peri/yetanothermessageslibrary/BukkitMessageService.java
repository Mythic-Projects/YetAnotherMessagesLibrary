package dev.peri.yetanothermessageslibrary;

import dev.peri.yetanothermessageslibrary.message.BukkitMessageDispatcher;
import dev.peri.yetanothermessageslibrary.viewer.BukkitViewerService;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BukkitMessageService<C extends MessageRepository> extends SimpleSendableMessageService<CommandSender, C, BukkitMessageDispatcher<?>> {

    public BukkitMessageService(@NotNull ViewerService<CommandSender> viewerService) {
        super(
                viewerService,
                BukkitMessageDispatcher::new
        );
    }

    public BukkitMessageService(@NotNull JavaPlugin plugin, @NotNull BukkitAudiences adventure) {
        this(new BukkitViewerService(plugin, adventure));
    }

}
