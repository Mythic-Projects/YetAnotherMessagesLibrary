package pl.peridot.yetanothermessageslibrary.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.locale.LocaleProvider;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.replace.replacement.Replacement;

public class MessageDispatcher<R> {

    private final AudienceSupplier<R> audienceSupplier;
    @SuppressWarnings("rawtypes")
    private final LocaleProvider localeProvider;

    private final Function<Object, Sendable> messageSupplier;

    private final List<Replaceable> replaceables = new ArrayList<>();
    private final List<TypedReplaceableSupplier> replaceableSuppliers = new ArrayList<>();

    public MessageDispatcher(@NotNull AudienceSupplier<R> audienceSupplier, @SuppressWarnings("rawtypes") @NotNull LocaleProvider localeProvider, @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier) {
        this.audienceSupplier = audienceSupplier;
        this.localeProvider = localeProvider;
        this.messageSupplier = messageSupplier;
    }

    public MessageDispatcher<R> with(@NotNull Replaceable replaceable) {
        this.replaceables.add(replaceable);
        return this;
    }

    public MessageDispatcher<R> with(@NotNull Replaceable... replaceables) {
        Collections.addAll(this.replaceables, replaceables);
        return this;
    }

    public <T extends R> MessageDispatcher<R> with(@NotNull Class<T> requiredType, @NotNull Function<T, Replaceable> replaceableSupplier) {
        this.replaceableSuppliers.add(new TypedReplaceableSupplier<>(requiredType, replaceableSupplier));
        return this;
    }

    public MessageDispatcher<R> with(@NotNull String from, @NotNull Object to) {
        return this.with(Replacement.of(from, to));
    }

    public MessageDispatcher<R> with(@NotNull String from, @NotNull Supplier<@NotNull Object> to) {
        return this.with(Replacement.of(from, to));
    }

    @SuppressWarnings("unchecked")
    public void sendTo(@Nullable R receiver) {
        if (receiver == null) {
            return;
        }

        Locale locale = this.localeProvider.getLocale(receiver);
        Audience audience = this.audienceSupplier.getAudience(receiver);
        boolean console = this.audienceSupplier.isConsole(receiver);

        Sendable message = this.messageSupplier.apply(locale);
        if (message == null) {
            return;
        }

        List<Replaceable> replaceables = new ArrayList<>(this.replaceables);
        this.replaceableSuppliers
                .stream()
                .filter(supplier -> supplier.getType().isInstance(receiver))
                .map(supplier -> {
                    Function<Object, Replaceable> replaceableFunction = supplier.getSupplier();
                    return replaceableFunction.apply(supplier.getType().cast(receiver));
                })
                .forEachOrdered(replaceables::add);

        message.send(locale, audience, console, replaceables.toArray(new Replaceable[0]));
    }

    public void sendTo(@Nullable Locale locale, @NotNull Audience audience, boolean console) {
        Sendable message = this.messageSupplier.apply(locale);
        if (message == null) {
            return;
        }
        message.send(locale, audience, console, this.replaceables.toArray(new Replaceable[0]));
    }

    public void sendTo(@NotNull Audience audience, boolean console) {
        this.sendTo(null, audience, console);
    }

    public void sendTo(@NotNull Audience audience) {
        this.sendTo(audience, false);
    }

    private class TypedReplaceableSupplier<T extends R> {

        public final Class<T> type;
        public final Function<T, Replaceable> supplier;

        private TypedReplaceableSupplier(@NotNull Class<T> type, @NotNull Function<T, Replaceable> supplier) {
            this.type = type;
            this.supplier = supplier;
        }

        public Class<T> getType() {
            return this.type;
        }

        public Function<T, Replaceable> getSupplier() {
            return this.supplier;
        }

    }

}
