package dev.peri.yetanothermessageslibrary.message;

import dev.peri.yetanothermessageslibrary.replace.Replaceable;
import dev.peri.yetanothermessageslibrary.replace.replacement.Replacement;
import dev.peri.yetanothermessageslibrary.util.TriFunction;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
public class MessageDispatcher<R, D extends MessageDispatcher<R, ?>> {

    private final ViewerService<R> viewerService;
    private final Function<Object, Locale> localeSupplier;
    private final Function<Object, Sendable> messageSupplier;

    private final Set<R> receivers = Collections.newSetFromMap(new WeakHashMap<>());
    private final Set<Predicate<R>> predicates = new HashSet<>();
    private final Set<Consumer<R>> actions = new HashSet<>();

    private final Map<String, Object> fields = new WeakHashMap<>();
    private final List<Replaceable> replacements = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    private final List<ReplaceableSupplier> replacementSuppliers = new ArrayList<>();

    public MessageDispatcher(
            @NotNull ViewerService<R> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        this.viewerService = viewerService;
        this.localeSupplier = localeSupplier;
        this.messageSupplier = messageSupplier;
    }

    @Contract("_ -> this")
    public D receiver(@Nullable R receiver) {
        if (receiver == null) {
            return (D) this;
        }

        this.receivers.add(receiver);
        return (D) this;
    }

    @Contract("_ -> this")
    public D receivers(@NotNull Collection<? extends @Nullable R> receivers) {
        receivers.forEach(this::receiver);
        return (D) this;
    }

    @Contract("_ -> this")
    public D predicate(@NotNull Predicate<@NotNull R> predicate) {
        this.predicates.add(predicate);
        return (D) this;
    }

    @Contract("_, _ -> this")
    public <T> D predicate(@NotNull Class<T> requiredType, @NotNull Predicate<@NotNull T> predicate) {
        return this.predicate(receiver -> {
            if (!requiredType.isInstance(receiver)) {
                return false;
            }
            return predicate.test(requiredType.cast(receiver));
        });
    }

    @Contract("_ -> this")
    public D action(@NotNull Consumer<@NotNull R> action) {
        this.actions.add(action);
        return (D) this;
    }

    @Contract("_, _ -> this")
    public D field(@NotNull String key, @Nullable Object value) {
        this.fields.put(key, value);
        return (D) this;
    }

    @Contract("_, -> this")
    public D fields(@NotNull Map<@NotNull String, @Nullable Object> fields) {
        this.fields.putAll(fields);
        return (D) this;
    }

    @Contract("_ -> this")
    public D with(@NotNull Replaceable replacement) {
        this.replacements.add(replacement);
        return (D) this;
    }

    @Contract("_ -> this")
    public D with(@NotNull Replaceable @NotNull ... replacement) {
        Collections.addAll(this.replacements, replacement);
        return (D) this;
    }

    @Contract("_ -> this")
    public D with(@NotNull Iterable<? extends @NotNull Replaceable> replacements) {
        replacements.forEach(this.replacements::add);
        return (D) this;
    }

    @Contract("_, _ -> this")
    public D with(@NotNull String from, @NotNull Object to) {
        return this.with(Replacement.of(from, to));
    }

    @Contract("_, _ -> this")
    public D with(@NotNull String from, @NotNull Supplier<@NotNull Object> to) {
        return this.with(Replacement.of(from, to));
    }

    @Contract("_, _, _ -> this")
    public <T extends R> D with(
            @NotNull Class<T> requiredType,
            @NotNull TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> replacementSupplier,
            @Nullable TriFunction<@NotNull R, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> fallbackSupplier
    ) {
        this.replacementSuppliers.add(new ReplaceableSupplier<>(requiredType, replacementSupplier, fallbackSupplier));
        return (D) this;
    }

    @Contract("_, _ -> this")
    public <T extends R> D with(
            @NotNull Class<T> requiredType,
            @NotNull TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> replacementSupplier
    ) {
        return this.with(requiredType, replacementSupplier, null);
    }

    @Contract("_, _, _ -> this")
    public <T extends R> D with(
            @NotNull Class<T> requiredType,
            @NotNull Function<@NotNull T, ? extends @NotNull Replaceable> replacementSupplier,
            @Nullable Function<@NotNull R, ? extends @NotNull Replaceable> fallbackSupplier
    ) {
        return this.with(
                requiredType,
                (receiver, locale, fields) -> replacementSupplier.apply(receiver),
                fallbackSupplier != null
                        ? (receiver, locale, fields) -> fallbackSupplier.apply(receiver)
                        : null
        );
    }

    @Contract("_, _ -> this")
    public <T extends R> D with(
            @NotNull Class<T> requiredType,
            @NotNull Function<@NotNull T, ? extends @NotNull Replaceable> replacementSupplier
    ) {
        return this.with(requiredType, replacementSupplier, null);
    }

    @Contract("_, _ -> this")
    public <T extends R> D with(
            @NotNull Class<T> requiredType,
            @NotNull Collection<Function<@NotNull T, ? extends @NotNull Replaceable>> replacementSuppliers
    ) {
        replacementSuppliers.forEach(replacementSupplier -> this.with(requiredType, replacementSupplier));
        return (D) this;
    }

    @Contract(" -> this")
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
        List<Replaceable> replaceables = this.prepareReplacements(receiver, locale);
        message.send(locale, viewer, replaceables.toArray(new Replaceable[0]));
        this.actions.forEach(action -> action.accept(receiver));
    }

    protected @NotNull List<Replaceable> prepareReplacements(@NotNull R receiver, @NotNull Locale locale) {
        List<Replaceable> replacement = new ArrayList<>(this.replacements);
        this.replacementSuppliers
                .stream()
                .map(supplier -> {
                    return supplier.supplyReplacement(
                            supplier.getEntityType().cast(receiver),
                            locale,
                            this.fields
                    );
                })
                .filter(Objects::nonNull)
                .forEachOrdered(replacement::add);
        return replacement;
    }

    private class ReplaceableSupplier<T extends R> {

        private final Class<T> entityType;
        private final TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> supplier;
        private final TriFunction<@NotNull R, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> fallbackSupplier;

        private ReplaceableSupplier(
                @NotNull Class<T> entityType,
                @NotNull TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> supplier,
                @Nullable TriFunction<@NotNull R, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> fallbackSupplier
        ) {
            this.entityType = entityType;
            this.supplier = supplier;
            this.fallbackSupplier = fallbackSupplier;
        }

        public @NotNull Class<T> getEntityType() {
            return this.entityType;
        }

        public @Nullable Replaceable supplyReplacement(@NotNull R receiver, @NotNull Locale locale, @NotNull Map<@NotNull String, @Nullable Object> fields) {
            if (this.entityType.isInstance(receiver)) {
                return this.supplier.apply(this.entityType.cast(receiver), locale, fields);
            }

            if (this.fallbackSupplier == null) {
                return null;
            }
            return this.fallbackSupplier.apply(receiver, locale, fields);
        }

    }

}
