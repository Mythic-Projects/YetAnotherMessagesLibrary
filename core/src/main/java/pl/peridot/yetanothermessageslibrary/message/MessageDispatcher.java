package pl.peridot.yetanothermessageslibrary.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AudienceSupplier;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.replace.replacement.Replacement;

public class MessageDispatcher<R, D extends MessageDispatcher<R, D>> {

    private final AudienceSupplier<R> audienceSupplier;
    private final Function<Object, Locale> localeSupplier;
    private final Function<Object, Sendable> messageSupplier;

    private final Set<R> receivers = new HashSet<>();
    private final Set<Predicate<R>> predicates = new HashSet<>();

    private final List<Replaceable> replacement = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    private final List<TypedReplaceableSupplier> replacementsSuppliers = new ArrayList<>();

    public MessageDispatcher(@NotNull AudienceSupplier<R> audienceSupplier, @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier, @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier) {
        this.audienceSupplier = audienceSupplier;
        this.localeSupplier = localeSupplier;
        this.messageSupplier = messageSupplier;
    }

    public D receiver(@NotNull R receiver) {
        this.receivers.add(receiver);
        return (D) this;
    }

    public D receivers(@NotNull Collection<? extends R> receivers) {
        this.receivers.addAll(receivers);
        return (D) this;
    }

    public D predicate(@NotNull Predicate<R> predicate) {
        this.predicates.add(predicate);
        return (D) this;
    }

    public D with(@NotNull Replaceable replacement) {
        this.replacement.add(replacement);
        return (D) this;
    }

    public D with(@NotNull Replaceable... replacement) {
        Collections.addAll(this.replacement, replacement);
        return (D) this;
    }

    public <T extends R> D with(@NotNull Class<T> requiredType, @NotNull Function<T, Replaceable> replacementSupplier) {
        this.replacementsSuppliers.add(new TypedReplaceableSupplier<>(requiredType, replacementSupplier));
        return (D) this;
    }

    public D with(@NotNull String from, @NotNull Object to) {
        return this.with(Replacement.of(from, to));
    }

    public D with(@NotNull String from, @NotNull Supplier<@NotNull Object> to) {
        return this.with(Replacement.of(from, to));
    }

    @SuppressWarnings("unchecked")
    public D sendTo(@Nullable R receiver) {
        if (receiver == null) {
            return (D) this;
        }

        Locale locale = this.localeSupplier.apply(receiver);
        Audience audience = this.audienceSupplier.getAudience(receiver);
        boolean console = this.audienceSupplier.isConsole(receiver);

        Sendable message = this.messageSupplier.apply(locale);
        if (message == null) {
            return (D) this;
        }

        if (this.predicates.stream().anyMatch(predicate -> !predicate.test(receiver))) {
            return (D) this;
        }

        List<Replaceable> replaceables = this.prepareReplacements(receiver);
        message.send(locale, audience, console, replaceables.toArray(new Replaceable[0]));

        return (D) this;
    }

    public D sendTo(@NotNull Collection<? extends R> receivers) {
        receivers.forEach(this::sendTo);
        return (D) this;
    }

    public D send() {
        return this.sendTo(this.receivers);
    }

    @SuppressWarnings("unchecked")
    protected @NotNull List<Replaceable> prepareReplacements(@NotNull R receiver) {
        List<Replaceable> replacement = new ArrayList<>(this.replacement);
        this.replacementsSuppliers
                .stream()
                .filter(supplier -> supplier.getType().isInstance(receiver))
                .map(supplier -> {
                    Function<Object, Replaceable> replaceableFunction = supplier.getSupplier();
                    return replaceableFunction.apply(supplier.getType().cast(receiver));
                })
                .forEachOrdered(replacement::add);
        return replacement;
    }

    private final class TypedReplaceableSupplier<T extends R> {

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
