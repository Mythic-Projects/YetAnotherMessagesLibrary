package dev.peri.yetanothermessageslibrary;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.peri.yetanothermessageslibrary.message.VelocityMessageDispatcher;
import dev.peri.yetanothermessageslibrary.viewer.VelocityViewerService;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import org.jetbrains.annotations.NotNull;

public class VelocityMessageService<C extends MessageRepository> extends SimpleSendableMessageService<CommandSource, C, VelocityMessageDispatcher<?>> {

    public VelocityMessageService(@NotNull ViewerService<CommandSource> viewerService) {
        super(
                viewerService,
                VelocityMessageDispatcher::new
        );
    }

    public VelocityMessageService(@NotNull ProxyServer proxyServer,  @NotNull Object plugin) {
        this(new VelocityViewerService(proxyServer, plugin));
    }

}
