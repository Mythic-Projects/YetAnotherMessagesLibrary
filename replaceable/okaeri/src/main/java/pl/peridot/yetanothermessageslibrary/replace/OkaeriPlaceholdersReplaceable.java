package pl.peridot.yetanothermessageslibrary.replace;

import eu.okaeri.placeholders.Placeholders;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;

public class OkaeriPlaceholdersReplaceable implements Replaceable {

    private static final Pattern FIELD_PATTERN = Pattern.compile("\\{(?<content>[^}]+)\\}");

    private final Placeholders placeholders;
    private final Consumer<PlaceholderContext> applyContexts;

    public OkaeriPlaceholdersReplaceable(@NotNull Placeholders placeholders, @NotNull Consumer<PlaceholderContext> applyContexts) {
        this.placeholders = placeholders;
        this.applyContexts = applyContexts;
    }

    @Override
    public @NotNull String replace(@NotNull String text, boolean ignoreCase) {
        return this.replacePlaceholders(text);
    }

    @Override
    public @NotNull Component replace(@NotNull Component text) {
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .match(FIELD_PATTERN)
                .replacement((result, input) -> Component.text(this.replacePlaceholders("{" + result.group() + "}")))
                .build();
        return text.replaceText(replacement);
    }

    private String replacePlaceholders(String text) {
        PlaceholderContext context = this.placeholders.contextOf(CompiledMessage.of(text));
        this.applyContexts.accept(context);
        return context.apply();
    }

}
