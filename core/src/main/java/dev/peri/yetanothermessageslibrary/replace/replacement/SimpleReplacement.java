package dev.peri.yetanothermessageslibrary.replace.replacement;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleReplacement extends Replacement {

    private final String to;

    protected SimpleReplacement(@NotNull String from, @Nullable Object to) {
        super(from);
        this.to = Objects.toString(to);
    }

    @Override
    public @NotNull String getTo() {
        return this.to;
    }

}
