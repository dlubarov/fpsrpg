package msg;

import msg.c2s.*;
import msg.s2c.*;
import ser.*;

// Serializes a message WITH its message type.

public final class MessageSerializer extends Serializer<Message> {
    public static final MessageSerializer singleton = new MessageSerializer();
    private MessageSerializer() {}

    @SuppressWarnings("unchecked")
    private static final Serializer<? extends Message>[] messageSerializers = new Serializer[] {
        NewAccountMessage.MySerializer.singleton,
        MotionRequestMessage.MySerializer.singleton,
        NewAccountErrorMessage.MySerializer.singleton,
        PeerGoodbyeMessage.MySerializer.singleton,
        PeerUpdateMessage.MySerializer.singleton,
        PlayerIntroductionMessage.MySerializer.singleton,
        WelcomeMessage.MySerializer.singleton,
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
