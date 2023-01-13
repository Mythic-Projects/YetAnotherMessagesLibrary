package pl.peridot.yetanothermessageslibrary.lang;

import java.util.Locale;
import org.jetbrains.annotations.Nullable;

public interface LocaleProvider<R> {

    LocaleProvider<?> EMPTY_PROVIDER = (LocaleProvider<Object>) receiver -> null;

    @Nullable Locale getLocale(@Nullable R receiver);

    static <R> LocaleProvider<R> empty() {
        return (LocaleProvider<R>) EMPTY_PROVIDER;
    }

}
