package dev.peri.yetanothermessageslibrary.config.serdes;

import dev.peri.yetanothermessageslibrary.adventure.AdventureHelper;
import dev.peri.yetanothermessageslibrary.adventure.RawComponent;
import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import net.kyori.adventure.text.Component;

public class ComponentTransformer extends BidirectionalTransformer<RawComponent, Component> {

    @Override
    public GenericsPair<RawComponent, Component> getPair() {
        return this.genericsPair(RawComponent.class, Component.class);
    }

    @Override
    public Component leftToRight(RawComponent data, SerdesContext serdesContext) {
        return data.getComponent();
    }

    @Override
    public RawComponent rightToLeft(Component data, SerdesContext serdesContext) {
        return new RawComponent(AdventureHelper.componentToAmpersandString(data), data);
    }

}
