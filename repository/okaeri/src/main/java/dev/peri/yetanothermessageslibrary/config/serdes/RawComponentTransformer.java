package dev.peri.yetanothermessageslibrary.config.serdes;

import dev.peri.yetanothermessageslibrary.adventure.MiniComponent;
import dev.peri.yetanothermessageslibrary.adventure.RawComponent;
import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;

public class RawComponentTransformer extends BidirectionalTransformer<String, RawComponent> {

    @Override
    public GenericsPair<String, RawComponent> getPair() {
        return this.genericsPair(String.class, RawComponent.class);
    }

    @Override
    public RawComponent leftToRight(String data, SerdesContext serdesContext) {
        return MiniComponent.of(data);
    }

    @Override
    public String rightToLeft(RawComponent data, SerdesContext serdesContext) {
        return data.getRaw();
    }

}
