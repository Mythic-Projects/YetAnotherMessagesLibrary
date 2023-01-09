package pl.peridot.yetanothermessageslibrary;

import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;

public class SimpleMessageService<R, C extends MessageRepository> implements MessageService<R, C> {

    private final AudienceSupplier<R> audienceSupplier;
    private final C messageRepository;

    public SimpleMessageService(@NotNull AudienceSupplier<R> audienceSupplier, C messageRepository) {
        this.audienceSupplier = audienceSupplier;
        this.messageRepository = messageRepository;
    }

    @Override
    public @NotNull AudienceSupplier<R> getAudienceSupplier() {
        return this.audienceSupplier;
    }

    @Override
    public @NotNull C getMessageRepository(R receiver) {
        return this.messageRepository;
    }

}
