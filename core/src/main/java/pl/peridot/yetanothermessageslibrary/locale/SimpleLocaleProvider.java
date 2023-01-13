package pl.peridot.yetanothermessageslibrary.locale;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleLocaleProvider<R> implements LocaleProvider<R> {

    private final Collection<LocaleProvider<?>> localeProviders;

    protected SimpleLocaleProvider(@NotNull Collection<LocaleProvider<?>> localeProviders) {
        this.localeProviders = Collections.unmodifiableCollection(localeProviders);
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable Locale getLocale(@Nullable R receiver) {
        if (receiver == null) {
            return null;
        }

        LocaleProvider localeProvider = this.findLocaleProvider(receiver.getClass());
        if (localeProvider == null) {
            return null;
        }

        return localeProvider.getLocale(receiver);
    }

    private @Nullable LocaleProvider<?> findLocaleProvider(@NotNull Class<?> receiverType) {
        return this.localeProviders
                .stream()
                .filter(localeProvider -> localeProvider.supports(receiverType))
                .findAny()
                .orElse(null);
    }

    public static <R> @NotNull SimpleLocaleProvider<R> of(@NotNull Collection<LocaleProvider<?>> localeProviders) {
        return new SimpleLocaleProvider<>(localeProviders);
    }

    public static <R> @NotNull SimpleLocaleProvider<R> of(@NotNull LocaleProvider<?>... localeProviders) {
        return of(Arrays.asList(localeProviders));
    }

}
