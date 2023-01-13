package pl.peridot.yetanothermessageslibrary;

import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.lang.LocaleProvider;

public class SimpleMessageService<R, C extends MessageRepository> implements MessageService<R, C> {

    private final AudienceSupplier<R> audienceSupplier;
    private final LocaleProvider<R> localeProvider;
    private final C messageRepository;

    public SimpleMessageService(@NotNull AudienceSupplier<R> audienceSupplier, @NotNull LocaleProvider<R> localeProvider, @NotNull C messageRepository) {
        this.audienceSupplier = audienceSupplier;
        this.messageRepository = messageRepository;
        this.localeProvider = localeProvider;
    }

    @Override
    public @NotNull AudienceSupplier<R> getAudienceSupplier() {
        return this.audienceSupplier;
    }

    @Override
    public @NotNull LocaleProvider<R> getLocaleProvider() {
        return this.localeProvider;
    }

    @Override
    public @NotNull C getMessageRepository(R receiver) {
        return this.messageRepository;
    }

}
