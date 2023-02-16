package dev.peri.yetanothermessageslibrary.config.serdes.holder;

import dev.peri.yetanothermessageslibrary.adventure.RawComponent;
import dev.peri.yetanothermessageslibrary.message.holder.impl.ChatHolder;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.ArrayList;
import java.util.List;

public class ChatSerializer implements ObjectSerializer<ChatHolder> {

    @Override
    public boolean supports(Class<? super ChatHolder> type) {
        return ChatHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ChatHolder holder, SerializationData data, GenericsDeclaration generics) {
        boolean sendOnlyToConsole = holder.sendOnlyToConsole();
        List<RawComponent> messages = holder.getMessages();

        if (!sendOnlyToConsole) {
            Object value = messages.size() == 1 ? messages.get(0) : messages;
            data.setValue(value);
            return;
        }

        data.add("console", true);
        if (messages.size() == 1) {
            data.add("message", messages.get(0));
        } else {
            data.addCollection("message", messages, RawComponent.class);
        }
    }

    @Override
    public ChatHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        boolean onlyConsole = false;
        List<RawComponent> messages = new ArrayList<>();

        if (data.isValue()) {
            Object raw = data.getValueRaw();
            if (raw instanceof String) {
                messages.add(data.getValue(RawComponent.class));
            } else if (raw instanceof List) {
                messages.addAll(data.getValueAsList(RawComponent.class));
            }
        } else {
            onlyConsole = data.containsKey("console")
                    ? data.get("console", boolean.class)
                    : false;

            Object raw = data.getRaw("message");
            if (raw instanceof String) {
                messages.add(data.get("message", RawComponent.class));
            } else if (raw instanceof List) {
                messages.addAll(data.getAsList("message", RawComponent.class));
            }
        }

        return new ChatHolder(onlyConsole, messages);
    }

}
