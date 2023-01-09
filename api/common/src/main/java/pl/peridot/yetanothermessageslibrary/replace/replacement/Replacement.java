package pl.peridot.yetanothermessageslibrary.replace.replacement;

import java.util.function.Supplier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import pl.peridot.yetanothermessageslibrary.replace.Replaceable;
import pl.peridot.yetanothermessageslibrary.replace.StringReplacer;

public abstract class Replacement implements Replaceable {

    private final String from;

    protected Replacement(String from) {
        this.from = from;
    }

    public String getFrom() {
        return this.from;
    }

    public abstract String getTo();

    @Override
    public String replace(String text, boolean ignoreCase) {
        return text.replace(this.getFrom(), this.getTo()); // TODO: ignoreCase
    }

    @Override
    public Component replace(Component text) {
        return text.replaceText(TextReplacementConfig.builder()
                .matchLiteral(this.getFrom())
                .replacement(this.getTo())
                .build());
    }

    public static SimpleReplacement of(String from, String to) {
        return new SimpleReplacement(from, to);
    }

    public static SimpleReplacement of(String from, Object to) {
        return new SimpleReplacement(from, to);
    }

    public static SupplierReplacement of(String from, Supplier<Object> to) {
        return new SupplierReplacement(from, to);
    }

    public static SimpleReplacement of(String from, String to, Replacement... replacements) {
        return of(from, StringReplacer.replace(to, replacements));
    }

    public static SupplierReplacement of(String from, Supplier<Object> to, Replacement... replacements) {
        return new SupplierReplacement(from, to, replacements);
    }

}
