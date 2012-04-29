package msg.s2c;

import ser.*;

public class PeerGoodbyeMessage extends ServerMessage {
    public final int id;

    public PeerGoodbyeMessage(int id) {
        super(MySerializer.singleton);
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
        public void serialize(PeerGoodbyeMessage msg, ByteSink sink) {
            IntegerSerializer.singleton.serialize(msg.id, sink);
        }

        @Override
        public PeerGoodbyeMessage deserialize(ByteSource source) {
            int id = IntegerSerializer.singleton.deserialize(source);
            return new PeerGoodbyeMessage(id);
        }
    }
}
