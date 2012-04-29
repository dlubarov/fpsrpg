package msg.s2c;

import msg.MessageType;
import ser.*;

public class PeerGoodbyeMessage extends ServerMessage {
    public final int id;

    public PeerGoodbyeMessage(int id) {
        super(MessageType.PEER_INRODUCTION);
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("PeerGoodbye(%d)", id);
    }

    public static class MySerializer extends Serializer<PeerGoodbyeMessage> {
        public static final MySerializer singleton = new MySerializer();
        private MySerializer() {}

        @Override
        public byte[] serialize(PeerGoodbyeMessage msg) {
            return IntegerSerializer.singleton.serialize(msg.id);
        }

        @Override
        public PeerGoodbyeMessage deserialize(byte[] data, int offset, int len) {
            int id = IntegerSerializer.singleton.deserialize(data, offset, len);
            return new PeerGoodbyeMessage(id);
        }
    }
}
