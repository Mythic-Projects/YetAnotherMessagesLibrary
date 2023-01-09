package pl.peridot.yetanothermessageslibrary.config.serdes.holder;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import pl.peridot.yetanothermessageslibrary.util.adventure.RawComponent;

public class ActionBarSerializer implements ObjectSerializer<ActionBarHolder> {

    @Override
    public boolean supports(Class<? super ActionBarHolder> type) {
        return ActionBarHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ActionBarHolder holder, SerializationData data, GenericsDeclaration generics) {
        data.add("message", holder.getMessage());
    }

    @Override
    public ActionBarHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        return new ActionBarHolder(data.get("message", RawComponent.class));
    }

}
