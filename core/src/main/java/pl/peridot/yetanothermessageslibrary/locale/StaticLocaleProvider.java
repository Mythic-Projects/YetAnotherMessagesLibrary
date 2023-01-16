package pl.peridot.yetanothermessageslibrary.locale;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StaticLocaleProvider<R> implements LocaleProvider<R> {

    private final Locale locale;

    protected StaticLocaleProvider(@Nullable Locale locale) {
        this.locale = locale;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return true;
    }

    @Override
    public @Nullable Locale getLocale(@NotNull R entity) {
        return this.locale;
    }

    public static <R> @NotNull StaticLocaleProvider<R> of(@Nullable Locale locale) {
        return new StaticLocaleProvider<>(locale);
    }

}
