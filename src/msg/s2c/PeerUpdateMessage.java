package msg.s2c;

import math.Vector3;
import ser.*;

public class PeerUpdateMessage extends ServerMessage {
    public final int id;
    public final Vector3 position;

    public PeerUpdateMessage(int id, Vector3 position) {
        super(MySerializer.singleton);
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
        public void serialize(PeerUpdateMessage msg, ByteSink sink) {
            IntegerSerializer.singleton.serialize(msg.id, sink);
            Vector3Serializer.singleton.serialize(msg.position, sink);
        }

        @Override
        public PeerUpdateMessage deserialize(ByteSource source) {
            int id = IntegerSerializer.singleton.deserialize(source);
            Vector3 position = Vector3Serializer.singleton.deserialize(source);
            return new PeerUpdateMessage(id, position);
        }
    }
}
