package dev.peri.yetanothermessageslibrary;

import dev.peri.yetanothermessageslibrary.message.BungeeMessageDispatcher;
import dev.peri.yetanothermessageslibrary.viewer.BungeeViewerService;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BungeeMessageService <C extends MessageRepository> extends SimpleSendableMessageService<CommandSender, C, BungeeMessageDispatcher<?>> {

    public BungeeMessageService(@NotNull ViewerService<CommandSender> viewerService) {
        super(
                viewerService,
                BungeeMessageDispatcher::new
        );
    }

    public BungeeMessageService(@NotNull Plugin plugin, @NotNull BungeeAudiences adventure) {
        this(new BungeeViewerService(plugin, adventure));
    }

}
