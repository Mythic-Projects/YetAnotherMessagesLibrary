package pl.peridot.yetanothermessageslibrary.config.serdes.holder;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.ArrayList;
import java.util.List;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.ChatHolder;
import pl.peridot.yetanothermessageslibrary.util.adventure.RawComponent;

public class ChatSerializer implements ObjectSerializer<ChatHolder> {

    @Override
    public boolean supports(Class<? super ChatHolder> type) {
        return ChatHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ChatHolder holder, SerializationData data, GenericsDeclaration generics) {
        if (holder.sendOnlyToConsole()) {
            data.add("console", true);
        }

        List<RawComponent> messages = holder.getMessages();
        if (messages.size() > 1) {
            data.addCollection("message", messages, RawComponent.class);
        } else if (messages.size() == 1) {
            data.add("message", messages.get(0));
        }
    }

    @Override
    public ChatHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        boolean onlyConsole = data.containsKey("console")
                ? data.get("console", boolean.class)
                : false;

        List<RawComponent> messages = new ArrayList<>();
        Object raw = data.getRaw("message");
        if (raw instanceof String) {
            messages.add(data.get("message", RawComponent.class));
        } else if (raw instanceof List) {
            messages.addAll(data.getAsList("message", RawComponent.class));
        }

        return new ChatHolder(onlyConsole, messages);
    }

}
