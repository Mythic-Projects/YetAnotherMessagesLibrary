package pl.peridot.yetanothermessageslibrary.replace.replacement;

import java.util.Objects;
import java.util.function.Supplier;
import pl.peridot.yetanothermessageslibrary.replace.StringReplacer;

public class SupplierReplacement extends Replacement {

    private final Supplier<Object> to;
    private final Replacement[] replacements;

    SupplierReplacement(String from, Supplier<Object> to, Replacement[] replacements) {
        super(from);
        this.to = to;
        this.replacements = replacements;
    }

    SupplierReplacement(String from, Supplier<Object> to) {
        this(from, to, new Replacement[0]);
    }

    @Override
    public String getTo() {
        return StringReplacer.replace(Objects.toString(this.to.get()), this.replacements);
    }

}
