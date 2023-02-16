package dev.peri.yetanothermessageslibrary.config.serdes.holder;

import dev.peri.yetanothermessageslibrary.adventure.RawComponent;
import dev.peri.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;

public class ActionBarHolderTransformer extends BidirectionalTransformer<RawComponent, ActionBarHolder> {

    @Override
    public GenericsPair<RawComponent, ActionBarHolder> getPair() {
        return this.genericsPair(RawComponent.class, ActionBarHolder.class);
    }

    @Override
    public ActionBarHolder leftToRight(RawComponent data, SerdesContext serdesContext) {
        return new ActionBarHolder(data);
    }

    @Override
    public RawComponent rightToLeft(ActionBarHolder data, SerdesContext serdesContext) {
        return data.getMessage();
    }

}
