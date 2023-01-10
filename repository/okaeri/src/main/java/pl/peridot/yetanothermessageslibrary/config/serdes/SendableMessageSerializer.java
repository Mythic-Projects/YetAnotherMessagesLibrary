package pl.peridot.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.ArrayList;
import java.util.List;
import pl.peridot.yetanothermessageslibrary.message.SendableMessage;
import pl.peridot.yetanothermessageslibrary.message.holder.SendableHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.ChatHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.SoundHolder;
import pl.peridot.yetanothermessageslibrary.message.holder.impl.TitleHolder;

public class SendableMessageSerializer implements ObjectSerializer<SendableMessage> {

    @Override
    public boolean supports(Class<? super SendableMessage> type) {
        return SendableMessage.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(SendableMessage message, SerializationData data, GenericsDeclaration generics) {
        List<SendableHolder> holders = message.getHolders();
        if (holders.size() == 1) {
            SendableHolder holder = holders.get(0);
            if (holder instanceof ChatHolder) {
                data.setValue(holder);
                return;
            }
        }

        List<ChatHolder> chatHolders = new ArrayList<>();
        List<BossBarHolder> bossBarHolders = new ArrayList<>();
        List<SoundHolder> soundHolders = new ArrayList<>();

        for (SendableHolder holder : message.getHolders()) {
            if (holder instanceof ChatHolder) {
                chatHolders.add((ChatHolder) holder);
                this.serializeHolders("chat", chatHolders, data, ChatHolder.class);
            } else if (holder instanceof ActionBarHolder) {
                data.add("actionbar", holder);
            } else if (holder instanceof TitleHolder) {
                data.add("title", holder);
            } else if (holder instanceof BossBarHolder) {
                bossBarHolders.add((BossBarHolder) holder);
                this.serializeHolders("bossbar", bossBarHolders, data, BossBarHolder.class);
            } else if (holder instanceof SoundHolder) {
                soundHolders.add((SoundHolder) holder);
                this.serializeHolders("sound", soundHolders, data, SoundHolder.class);
            }
        }
    }

    private <T extends SendableHolder> void serializeHolders(String key, List<T> holders, SerializationData data, Class<? extends T> type) {
        if (holders.size() > 1) {
            data.addCollection(key, holders, type);
        } else if (holders.size() == 1) {
            data.add(key, holders.get(0));
        }
    }

    @Override
    public SendableMessage deserialize(DeserializationData data, GenericsDeclaration generics) {
        if (data.isValue()) {
            Object raw = data.getValueRaw();
            if (raw instanceof String) {
                return new SendableMessage(data.getValue(ChatHolder.class));
            }
        }

        List<SendableHolder> messageHolders = new ArrayList<>();

        messageHolders.addAll(this.deserializeHolders("chat", data, ChatHolder.class));
        if (data.containsKey("actionbar")) {
            messageHolders.add(data.get("actionbar", ActionBarHolder.class));
        }
        if (data.containsKey("title")) {
            messageHolders.add(data.get("title", TitleHolder.class));
        }
        messageHolders.addAll(this.deserializeHolders("bossbar", data, BossBarHolder.class));
        messageHolders.addAll(this.deserializeHolders("sound", data, SoundHolder.class));

        return new SendableMessage(messageHolders);
    }

    private <T extends SendableHolder> List<T> deserializeHolders(String key, DeserializationData data, Class<? extends T> type) {
        List<T> holders = new ArrayList<>();
        if (data.containsKey(key)) {
            if (data.getRaw(key) instanceof List<?>) {
                holders.addAll(data.getAsList(key, type));
            } else {
                holders.add(data.get(key, type));
            }
        }
        return holders;
    }

}
