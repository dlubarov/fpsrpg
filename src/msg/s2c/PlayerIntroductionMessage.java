package msg.s2c;

import math.Vector3;
import msg.MessageType;
import ser.*;

public class PlayerIntroductionMessage extends ServerMessage {
    public final String username;
    public final Vector3 position;

    public PlayerIntroductionMessage(String username, Vector3 position) {
        super(MessageType.PEER_INRODUCTION);
        this.username = username;
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("PlayerIntroduction(%s)", username);
    }

    public static class MySerializer extends Serializer<PlayerIntroductionMessage> {
        public static final MySerializer singleton = new MySerializer();
        private MySerializer() {}

        @Override
        public byte[] serialize(PlayerIntroductionMessage msg) {
            ByteBuilder buf = new ByteBuilder();
            buf.add((byte) MessageType.PEER_INRODUCTION.ordinal());
            byte[] usernameData = UTF8Serializer.singleton.serialize(msg.username);
            buf.addAll(IntegerSerializer.singleton.serialize(usernameData.length));
            buf.addAll(usernameData);
            buf.addAll(Vector3Serializer.singleton.serialize(msg.position));
            return buf.getContents();
        }

        @Override
        public PlayerIntroductionMessage deserialize(byte[] data, int offset, int len) {
            int usernameLen = IntegerSerializer.singleton.deserialize(data, offset, 4);
            offset += 4;
            String username = UTF8Serializer.singleton.deserialize(data, offset, usernameLen);
            offset += usernameLen;
            Vector3 position = Vector3Serializer.singleton.deserialize(data, offset, 24);
            return new PlayerIntroductionMessage(username, position);
        }
    }
}
