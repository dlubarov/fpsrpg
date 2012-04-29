package msg;

import msg.c2s.NewAccountMessage;
import msg.s2c.*;
import ser.Serializer;

public final class MessageSerializer extends Serializer<Message> {
    public static final MessageSerializer singleton = new MessageSerializer();
    private MessageSerializer() {}

    @Override
    public byte[] serialize(Message msg) {
        switch (msg.type) {
            case NEW_ACCOUNT:
                return NewAccountMessage.MySerializer.singleton.serialize((NewAccountMessage) msg);
            case NEW_ACCOUNT_ERROR:
                return NewAccountErrorMessage.MySerializer.singleton.serialize((NewAccountErrorMessage) msg);
            case WELCOME:
                return WelcomeMessage.MySerializer.singleton.serialize((WelcomeMessage) msg);
            case PEER_INRODUCTION:
                return PlayerIntroductionMessage.MySerializer.singleton.serialize((PlayerIntroductionMessage) msg);
            case PEER_UPDATE:
                return PeerUpdateMessage.MySerializer.singleton.serialize((PeerUpdateMessage) msg);
            case PEER_GOODBYE:
                return PeerGoodbyeMessage.MySerializer.singleton.serialize((PeerGoodbyeMessage) msg);
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
            case NEW_ACCOUNT_ERROR:
                return NewAccountErrorMessage.MySerializer.singleton.deserialize(data, offset, len);
            case WELCOME:
                return WelcomeMessage.MySerializer.singleton.deserialize(data, offset, len);
            case PEER_INRODUCTION:
                ;
            case PEER_UPDATE:
                ;
            case PEER_GOODBYE:
                ;
            default:
                throw new AssertionError("Forgot a case?");
        }
    }
}
