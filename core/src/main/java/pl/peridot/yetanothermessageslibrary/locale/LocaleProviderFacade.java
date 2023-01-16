package pl.peridot.yetanothermessageslibrary.locale;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocaleProviderFacade implements LocaleProvider<Object> {

    private final Collection<LocaleProvider<?>> localeProviders;

    protected LocaleProviderFacade(@NotNull Collection<LocaleProvider<?>> localeProviders) {
        this.localeProviders = Collections.unmodifiableCollection(localeProviders);
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return true;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public @Nullable Locale getLocale(@NotNull Object entity) {
        LocaleProvider localeProvider = this.findLocaleProvider(entity.getClass());
        if (localeProvider == null) {
            return null;
        }
        return localeProvider.getLocale(entity);
    }

    private @Nullable LocaleProvider<?> findLocaleProvider(@NotNull Class<?> receiverType) {
        return this.localeProviders
                .stream()
                .filter(localeProvider -> localeProvider.supports(receiverType))
                .findAny()
                .orElse(null);
    }

    public static @NotNull LocaleProviderFacade of(@NotNull Collection<LocaleProvider<?>> localeProviders) {
        return new LocaleProviderFacade(localeProviders);
    }

    public static @NotNull LocaleProviderFacade of(@NotNull LocaleProvider<?>... localeProviders) {
        return of(Arrays.asList(localeProviders));
    }

}
