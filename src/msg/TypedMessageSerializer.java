package msg;

import msg.c2s.NewAccountMessage;
import ser.*;

public final class TypedMessageSerializer extends Serializer<Message> {
    public static final TypedMessageSerializer singleton = new TypedMessageSerializer();
    private TypedMessageSerializer() {}

    @SuppressWarnings("unchecked")
    private static final Serializer<? extends Message>[] messageSerializers = new Serializer[] {
        NewAccountMessage.MySerializer.singleton,
    };

    // TODO: Use a map to avoid linear scan.
    private int getMessageID(Serializer<? extends Message> serializer) {
        for (int i = 0; i < messageSerializers.length; ++i)
            if (serializer == messageSerializers[i])
                return i;
        throw new IllegalArgumentException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void serialize(Message msg, ByteSink sink) {
        int msgID = getMessageID(msg.serializer);
        IntegerSerializer.singleton.serialize(msgID, sink);
        // Totally abusing Java's unchecked casts due to erasure. :-)
        ((Serializer<Message>) msg.serializer).serialize(msg, sink);
    }

    @Override
    public Message deserialize(ByteSource source) {
        int typeID = IntegerSerializer.singleton.deserialize(source);
        return messageSerializers[typeID].deserialize(source);
    }
}
