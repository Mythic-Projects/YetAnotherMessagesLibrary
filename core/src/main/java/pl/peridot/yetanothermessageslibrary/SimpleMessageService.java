package pl.peridot.yetanothermessageslibrary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.locale.LocaleProvider;
import pl.peridot.yetanothermessageslibrary.locale.StaticLocaleProvider;
import pl.peridot.yetanothermessageslibrary.util.Validate;

public class SimpleMessageService<R, C extends MessageRepository> implements MessageService<R, C> {

    private final AudienceSupplier<R> audienceSupplier;
    private final Locale defaultLocale;
    private final LocaleProvider<R> localeProvider;
    private final Map<Locale, C> messageRepositories;

    protected SimpleMessageService(@NotNull AudienceSupplier<R> audienceSupplier, Locale defaultLocale, @NotNull LocaleProvider<R> localeProvider, @NotNull Map<Locale, C> messageRepositories) {
        this.audienceSupplier = audienceSupplier;
        this.defaultLocale = defaultLocale;
        this.messageRepositories = messageRepositories;
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

    private @NotNull Locale getLocale(@Nullable R receiver) {
        Locale locale = this.localeProvider.getLocale(receiver);
        if (locale == null) {
            return this.defaultLocale;
        }
        return locale;
    }

    @Override
    public @NotNull C getMessageRepository(@Nullable R receiver) {
        Locale locale = this.getLocale(receiver);

        C messageRepository = this.messageRepositories.get(locale);
        if (messageRepository == null) {
            messageRepository = this.messageRepositories.get(this.defaultLocale);
        }

        if (messageRepository == null) {
            throw new RuntimeException("No message repository for locale " + locale);
        }

        return messageRepository;
    }

    public static <R, C extends MessageRepository> @NotNull Builder<R, C> builder() {
        return new Builder<>();
    }

    private static class Builder<R, C extends MessageRepository> {

        private AudienceSupplier<R> audienceSupplier;
        private Locale defaultLocale;
        private LocaleProvider<R> localeProvider = StaticLocaleProvider.of(null);
        private final Map<Locale, C> messageRepositories = new HashMap<>();

        @Contract("_ -> this")
        public Builder<R, C> audienceSupplier(@NotNull AudienceSupplier<R> audienceSupplier) {
            this.audienceSupplier = audienceSupplier;
            return this;
        }

        @Contract("_ -> this")
        public Builder<R, C> defaultLocale(@NotNull Locale defaultLocale) {
            this.defaultLocale = defaultLocale;
            return this;
        }

        @Contract("_ -> this")
        public Builder<R, C> localeProvider(@NotNull LocaleProvider<R> localeProvider) {
            this.localeProvider = localeProvider;
            return this;
        }

        @Contract("_, _ -> this")
        public Builder<R, C> messageRepository(@NotNull Locale locale, @NotNull C messageRepository) {
            this.messageRepositories.put(locale, messageRepository);
            return this;
        }

        @Contract("_, _ -> this")
        public Builder<R, C> messageRepository(@NotNull Locale locale, @NotNull Function<@NotNull Locale, @NotNull C> messageRepositorySupplier) {
            return this.messageRepository(locale, messageRepositorySupplier.apply(locale));
        }

        @Contract("_, _ -> this")
        public Builder<R, C> messageRepositories(@NotNull Collection<Locale> locales, @NotNull Function<@NotNull Locale, @NotNull C> messageRepositorySupplier) {
            locales.forEach(locale -> this.messageRepository(locale, messageRepositorySupplier));
            return this;
        }

        @NotNull
        public SimpleMessageService<R, C> build() {
            Validate.notNull(this.audienceSupplier, "Audience supplier cannot be null");
            Validate.notNull(this.defaultLocale, "Default locale cannot be null");
            Validate.notNull(this.localeProvider, "Locale provider cannot be null");
            Validate.isFalse(this.messageRepositories.isEmpty(), "Message repositories cannot be empty");
            return new SimpleMessageService<>(this.audienceSupplier, this.defaultLocale, this.localeProvider, this.messageRepositories);
        }

    }

}
