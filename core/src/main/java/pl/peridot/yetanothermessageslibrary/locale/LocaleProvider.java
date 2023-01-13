package pl.peridot.yetanothermessageslibrary.locale;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LocaleProvider<T> {

    boolean supports(@NotNull Class<?> clazz);

    @Nullable Locale getLocale(@Nullable T receiver);

}
