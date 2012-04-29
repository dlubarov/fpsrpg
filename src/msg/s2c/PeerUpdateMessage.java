package msg.s2c;

import math.Vector3;
import msg.MessageType;
import ser.*;

public class PeerUpdateMessage extends ServerMessage {
    public final int id;
    public final Vector3 position;

    public PeerUpdateMessage(int id, Vector3 position) {
        super(MessageType.PEER_INRODUCTION);
        this.id = id;
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("PeerUpdate(%d, %d)", id, position);
    }

    public static class MySerializer extends Serializer<PeerUpdateMessage> {
        public static final MySerializer singleton = new MySerializer();
        private MySerializer() {}

        @Override
        public byte[] serialize(PeerUpdateMessage msg) {
            ByteBuilder buf = new ByteBuilder();
            buf.add((byte) MessageType.PEER_INRODUCTION.ordinal());
            buf.addAll(IntegerSerializer.singleton.serialize(msg.id));
            buf.addAll(Vector3Serializer.singleton.serialize(msg.position));
            return buf.getContents();
        }

        @Override
        public PeerUpdateMessage deserialize(byte[] data, int offset, int len) {
            int id = IntegerSerializer.singleton.deserialize(data, offset, 4);
            offset += 4;
            Vector3 position = Vector3Serializer.singleton.deserialize(data, offset, 24);
            return new PeerUpdateMessage(id, position);
        }
    }
}
