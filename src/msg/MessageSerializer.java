package msg;

import ser.Serializer;

public final class MessageSerializer extends Serializer<Message> {
    public static final MessageSerializer singleton = new MessageSerializer();
    private MessageSerializer() {}

    @Override
    public byte[] serialize(Message msg) {
        switch (msg.type) {
            case NEW_ACCOUNT:
                return NewAccountMessage.MySerializer.singleton.serialize((NewAccountMessage) msg);
            default:
                throw new AssertionError("Build a message with bad type.");
        }
    }

    @Override
    public Message deserialize(byte[] data, int offset, int len) {
        byte msgId = data[0];
        MessageType type;
        try {
            type = MessageType.values()[msgId];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf("Received a message with bad type %d.\n", msgId);
            return null;
        }
        ++offset; --len;

        switch (type) {
            case NEW_ACCOUNT:
                return NewAccountMessage.MySerializer.singleton.deserialize(data, offset, len);
            default:
                throw new AssertionError("Forgot a case?");
        }
    }
}
