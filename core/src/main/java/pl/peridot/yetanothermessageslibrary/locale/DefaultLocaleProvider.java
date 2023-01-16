package pl.peridot.yetanothermessageslibrary.locale;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * LocaleProvider implementation that fallbacks to default locale if no locale is found.
 */
public class DefaultLocaleProvider implements LocaleProvider<Object> {

    private final Locale defaultLocale;
    private final LocaleProvider localeProvider;

    public DefaultLocaleProvider(Locale defaultLocale, LocaleProvider localeProvider) {
        this.defaultLocale = defaultLocale;
        this.localeProvider = localeProvider;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return true;
    }

    public @NotNull Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public @NotNull Locale getLocale(@Nullable Object entity) {
        if (entity == null) {
            return this.defaultLocale;
        }

        Locale locale = this.localeProvider.getLocale(entity);
        if (locale == null) {
            return this.defaultLocale;
        }
        return locale;
    }

}
