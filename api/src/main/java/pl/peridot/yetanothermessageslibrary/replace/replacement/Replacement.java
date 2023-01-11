package pl.peridot.yetanothermessageslibrary.replace.replacement;

import java.util.function.Supplier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.replace.StringReplacer;

public abstract class Replacement implements Replaceable {

    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacySection();

    private final String from;

    protected Replacement(@NotNull String from) {
        this.from = from;
    }

    public @NotNull String getFrom() {
        return this.from;
    }

    public abstract @NotNull String getTo();

    @Contract(pure = true)
    public @NotNull String replace(@NotNull String text) {
        return text.replace(this.getFrom(), this.getTo());
    }

    @Contract(pure = true)
    @Override
    public @NotNull Component replace(@NotNull Component text) {
        return text.replaceText(TextReplacementConfig.builder()
                .matchLiteral(this.getFrom())
                .replacement(LEGACY_SERIALIZER.deserialize(this.getTo())) //TODO: Find better solution that doesn't require LegacyComponentSerializer
                .build());
    }

    public static @NotNull SimpleReplacement of(@NotNull String from, @Nullable Object to) {
        return new SimpleReplacement(from, to);
    }

    public static @NotNull SimpleReplacement of(@NotNull String from, @Nullable String to, @NotNull Replacement... replacements) {
        return of(from, StringReplacer.replace(to, replacements));
    }

    public static @NotNull SupplierReplacement of(@NotNull String from, @NotNull Supplier<@Nullable Object> to) {
        return new SupplierReplacement(from, to);
    }

    public static @NotNull SupplierReplacement of(@NotNull String from, @NotNull Supplier<@Nullable Object> to, @NotNull Replacement... replacements) {
        return new SupplierReplacement(from, to, replacements);
    }

}
