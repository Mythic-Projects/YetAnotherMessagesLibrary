package pl.peridot.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import pl.peridot.yetanothermessageslibrary.util.adventure.MiniComponent;
import pl.peridot.yetanothermessageslibrary.util.adventure.RawComponent;

public class RawComponentTransformer extends BidirectionalTransformer<String, RawComponent> {

    @Override
    public GenericsPair<String, RawComponent> getPair() {
        return this.genericsPair(String.class, RawComponent.class);
    }

    @Override
    public RawComponent leftToRight(String data, SerdesContext serdesContext) {
        return MiniComponent.ofLegacy(data);
    }

    @Override
    public String rightToLeft(RawComponent data, SerdesContext serdesContext) {
        return data.getRaw();
    }

}
