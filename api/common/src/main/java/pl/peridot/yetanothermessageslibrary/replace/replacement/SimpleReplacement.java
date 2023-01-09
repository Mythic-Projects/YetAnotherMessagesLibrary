package pl.peridot.yetanothermessageslibrary.replace.replacement;

import java.util.Objects;

public class SimpleReplacement extends Replacement {

    private final String to;

    SimpleReplacement(String from, String to) {
        super(from);
        this.to = to;
    }

    SimpleReplacement(String from, Object to) {
        this(from, Objects.toString(to));
    }

    @Override
    public String getTo() {
        return this.to;
    }

}
