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
import java.util.WeakHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
public class MessageDispatcher<R, D extends MessageDispatcher<R, ?>> {

    private final ViewerService<R> viewerService;
    private final Function<Object, Locale> localeSupplier;
    private final Function<Object, Sendable> messageSupplier;

    private final Set<R> receivers = Collections.newSetFromMap(new WeakHashMap<>());
    private final Set<Predicate<R>> predicates = new HashSet<>();

    private final List<Replaceable> replacements = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    private final List<TypedReplaceableSupplier> replacementsSuppliers = new ArrayList<>();

    public MessageDispatcher(
            @NotNull ViewerService<R> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        this.viewerService = viewerService;
        this.localeSupplier = localeSupplier;
        this.messageSupplier = messageSupplier;
    }

    public D receiver(@Nullable R receiver) {
        if (receiver == null) {
            return (D) this;
        }

        this.receivers.add(receiver);
        return (D) this;
    }

    public D receivers(@NotNull Collection<? extends @Nullable R> receivers) {
        receivers.forEach(this::receiver);
        return (D) this;
    }

    public D predicate(@NotNull Predicate<@NotNull R> predicate) {
        this.predicates.add(predicate);
        return (D) this;
    }

    public <T> D predicate(@NotNull Class<T> requiredType, @NotNull Predicate<@NotNull T> predicate) {
        this.predicates.add(receiver -> {
            if (!requiredType.isInstance(receiver)) {
                return false;
            }
            return predicate.test(requiredType.cast(receiver));
        });
        return (D) this;
    }

    public D with(@NotNull Replaceable replacement) {
        this.replacements.add(replacement);
        return (D) this;
    }

    public D with(@NotNull Replaceable... replacement) {
        Collections.addAll(this.replacements, replacement);
        return (D) this;
    }

    public D with(@NotNull Iterable<? extends Replaceable> replacements) {
        replacements.forEach(this.replacements::add);
        return (D) this;
    }

    public <T extends R> D with(@NotNull Class<T> requiredType, @NotNull Function<T, ? extends Replaceable> replacementSupplier) {
        this.replacementsSuppliers.add(new TypedReplaceableSupplier<>(requiredType, replacementSupplier));
        return (D) this;
    }

    public D with(@NotNull String from, @NotNull Object to) {
        return this.with(Replacement.of(from, to));
    }

    public D with(@NotNull String from, @NotNull Supplier<@NotNull Object> to) {
        return this.with(Replacement.of(from, to));
    }

    public D send() {
        this.receivers.forEach(this::sendTo);
        return (D) this;
    }

    private void sendTo(@NotNull R receiver) {
        Locale locale = this.localeSupplier.apply(receiver);

        Sendable message = this.messageSupplier.apply(locale);
        if (message == null) {
            return;
        }

        if (this.predicates.stream().anyMatch(predicate -> !predicate.test(receiver))) {
            return;
        }

        Viewer viewer = this.viewerService.findOrCreateViewer(receiver);
        List<Replaceable> replaceables = this.prepareReplacements(receiver);
        message.send(locale, viewer, replaceables.toArray(new Replaceable[0]));
    }

    protected @NotNull List<Replaceable> prepareReplacements(@NotNull R receiver) {
        List<Replaceable> replacement = new ArrayList<>(this.replacements);
        this.replacementsSuppliers
                .stream()
                .filter(supplier -> supplier.getType().isInstance(receiver))
                .map(supplier -> {
                    Function<Object, Replaceable> replaceableFunction = supplier.getSupplier();
                    return replaceableFunction.apply(supplier.getType());
                })
                .forEachOrdered(replacement::add);
        return replacement;
    }

    private final class TypedReplaceableSupplier<T extends R> {

        public final Class<T> type;
        public final Function<T, ? extends Replaceable> supplier;

        private TypedReplaceableSupplier(@NotNull Class<T> type, @NotNull Function<T, ? extends Replaceable> supplier) {
            this.type = type;
            this.supplier = supplier;
        }

        public Class<T> getType() {
            return this.type;
        }

        public Function<T, ? extends Replaceable> getSupplier() {
            return this.supplier;
        }

    }

}
