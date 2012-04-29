package msg.s2c;

import ser.*;
import msg.MessageType;

public class WelcomeMessage extends ServerMessage {
    public final int realmID;

    protected WelcomeMessage(int realmID) {
        super(MessageType.WELCOME);
        this.realmID = realmID;
    }

    @Override
    public String toString() {
        return String.format("Welcome(%d)", realmID);
    }

    public static class MySerializer extends Serializer<WelcomeMessage> {
        public static final MySerializer singleton = new MySerializer();
        private MySerializer() {}

        @Override
        public byte[] serialize(WelcomeMessage msg) {
            return IntegerSerializer.singleton.serialize(msg.realmID);
        }

        @Override
        public WelcomeMessage deserialize(byte[] data, int offset, int len) {
            int realmID = IntegerSerializer.singleton.deserialize(data, offset, len);
            return new WelcomeMessage(realmID);
        }
    }
}
