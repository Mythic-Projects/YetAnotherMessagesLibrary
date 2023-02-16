package dev.peri.yetanothermessageslibrary.replace.replacement;

import dev.peri.yetanothermessageslibrary.replace.StringReplacer;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SupplierReplacement extends Replacement {

    private final Supplier<Object> to;
    private final Replacement[] replacements;

    protected SupplierReplacement(@NotNull String from, @NotNull Supplier<@Nullable Object> to, @NotNull Replacement[] replacements) {
        super(from);
        this.to = to;
        this.replacements = replacements;
    }

    protected SupplierReplacement(@NotNull String from, @NotNull Supplier<@Nullable Object> to) {
        this(from, to, new Replacement[0]);
    }

    @Override
    public @NotNull String getTo() {
        return StringReplacer.replace(Objects.toString(this.to.get()), this.replacements);
    }

}
