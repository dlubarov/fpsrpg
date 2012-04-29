package msg;

import ser.Serializer;

public abstract class Message {
    public final Serializer<? extends Message> serializer;

    protected Message(Serializer<? extends Message> serializer) {
        this.serializer = serializer;
    }

    public final byte[] serializeWithType() {
        return MessageSerializer.singleton.serialize(this);
    }

    @Override
    public abstract String toString();
}
