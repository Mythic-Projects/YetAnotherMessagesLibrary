package pl.peridot.yetanothermessageslibrary;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.locale.DefaultLocaleProvider;
import pl.peridot.yetanothermessageslibrary.locale.LocaleProvider;
import pl.peridot.yetanothermessageslibrary.locale.StaticLocaleProvider;
import pl.peridot.yetanothermessageslibrary.util.Validate;

public class SimpleSendableMessageService<R, C extends MessageRepository> implements SendableMessageService<R, C> {

    private final AudienceSupplier<R> audienceSupplier;
    private final DefaultLocaleProvider localeProvider;
    private final Map<Locale, C> messageRepositories;

    protected SimpleSendableMessageService(@NotNull AudienceSupplier<R> audienceSupplier, @NotNull Locale defaultLocale, @NotNull LocaleProvider<R> localeProvider, @NotNull Map<Locale, C> messageRepositories) {
        this.audienceSupplier = audienceSupplier;
        this.localeProvider = new DefaultLocaleProvider(defaultLocale, localeProvider);
        this.messageRepositories = messageRepositories;
    }

    protected SimpleSendableMessageService(@NotNull AudienceSupplier<R> audienceSupplier, @NotNull Locale defaultLocale, @NotNull C messageRepository) {
        this(audienceSupplier, defaultLocale, StaticLocaleProvider.of(defaultLocale), Collections.singletonMap(defaultLocale, messageRepository));
    }

    @Override
    public @NotNull AudienceSupplier<R> getAudienceSupplier() {
        return this.audienceSupplier;
    }

    @Override
    public @NotNull DefaultLocaleProvider getLocaleProvider() {
        return this.localeProvider;
    }

    @Override
    public @NotNull Locale getDefaultLocale() {
        return this.localeProvider.getDefaultLocale();
    }

    @Override
    public @NotNull C getMessageRepository(@NotNull Locale locale) {
        C messageRepository = this.messageRepositories.get(locale);
        if (messageRepository == null) {
            // If we can't find message repository for language wariant (for e.g. en_GB) we will try to find message repository for language (for e.g. en)
            messageRepository = this.messageRepositories.get(Locale.forLanguageTag(locale.getLanguage()));
        }

        if (messageRepository == null) {
            messageRepository = this.messageRepositories.get(this.getDefaultLocale());
        }

        if (messageRepository == null) {
            throw new RuntimeException("No message repository found for locale " + locale);
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
        public SimpleSendableMessageService<R, C> build() {
            Validate.notNull(this.audienceSupplier, "Audience supplier cannot be null");
            Validate.notNull(this.defaultLocale, "Default locale cannot be null");
            Validate.notNull(this.localeProvider, "Locale provider cannot be null");
            Validate.isFalse(this.messageRepositories.isEmpty(), "Message repositories cannot be empty");
            return new SimpleSendableMessageService<>(this.audienceSupplier, this.defaultLocale, this.localeProvider, this.messageRepositories);
        }

    }

}
