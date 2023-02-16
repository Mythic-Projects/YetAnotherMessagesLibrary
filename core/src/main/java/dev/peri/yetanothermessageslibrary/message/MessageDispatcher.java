package dev.peri.yetanothermessageslibrary.message;

import dev.peri.yetanothermessageslibrary.replace.Replaceable;
import dev.peri.yetanothermessageslibrary.replace.replacement.Replacement;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
public class MessageDispatcher<R, D extends MessageDispatcher<R, D>> {

    private final ViewerService<R, ? extends Viewer> viewerService;
    private final Function<Object, Locale> localeSupplier;
    private final Function<Object, Sendable> messageSupplier;

    private final Set<R> receivers = new HashSet<>();
    private final Set<Predicate<R>> predicates = new HashSet<>();

    private final List<Replaceable> replacement = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    private final List<TypedReplaceableSupplier> replacementsSuppliers = new ArrayList<>();

    public MessageDispatcher(
            @NotNull ViewerService<R, ? extends Viewer> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        this.viewerService = viewerService;
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

    public D with(@NotNull Iterable<? extends Replaceable> replacements) {
        replacements.forEach(this.replacement::add);
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

    public D sendTo(@Nullable R receiver) {
        if (receiver == null) {
            return (D) this;
        }

        Locale locale = this.localeSupplier.apply(receiver);

        Sendable message = this.messageSupplier.apply(locale);
        if (message == null) {
            return (D) this;
        }

        if (this.predicates.stream().anyMatch(predicate -> !predicate.test(receiver))) {
            return (D) this;
        }

        Viewer viewer = this.viewerService.findOrCreateViewer(receiver);
        List<Replaceable> replaceables = this.prepareReplacements(receiver);
        message.send(locale, viewer, replaceables.toArray(new Replaceable[0]));

        return (D) this;
    }

    public D sendTo(@NotNull Collection<? extends R> receivers) {
        receivers.forEach(this::sendTo);
        return (D) this;
    }

    public D send() {
        return this.sendTo(this.receivers);
    }

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
