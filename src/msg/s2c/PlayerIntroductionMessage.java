package msg.s2c;

import math.Vector3;
import ser.*;

public class PlayerIntroductionMessage extends ServerMessage {
    public final String username;
    public final Vector3 position;

    public PlayerIntroductionMessage(String username, Vector3 position) {
        super(MySerializer.singleton);
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
        public void serialize(PlayerIntroductionMessage msg, ByteSink sink) {
            UTF8Serializer.singleton.serialize(msg.username, sink);
            Vector3Serializer.singleton.serialize(msg.position, sink);
        }

        @Override
        public PlayerIntroductionMessage deserialize(ByteSource source) {
            String username = UTF8Serializer.singleton.deserialize(source);
            Vector3 position = Vector3Serializer.singleton.deserialize(source);
            return new PlayerIntroductionMessage(username, position);
        }
    }
}
