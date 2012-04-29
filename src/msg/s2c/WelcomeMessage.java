package msg.s2c;

import env.Realm;
import ser.*;

public class WelcomeMessage extends ServerMessage {
    public final int realmID;

    public WelcomeMessage(int realmID) {
        super(MySerializer.singleton);
        this.realmID = realmID;
    }

    public WelcomeMessage(Realm realm) {
        this(realm.id);
    }

    @Override
    public String toString() {
        return String.format("Welcome(%d)", realmID);
    }

    public static class MySerializer extends Serializer<WelcomeMessage> {
        public static final MySerializer singleton = new MySerializer();
        private MySerializer() {}

        @Override
        public void serialize(WelcomeMessage msg, ByteSink sink) {
            IntegerSerializer.singleton.serialize(msg.realmID, sink);
        }

        @Override
        public WelcomeMessage deserialize(ByteSource source) {
            int realmID = IntegerSerializer.singleton.deserialize(source);
            return new WelcomeMessage(realmID);
        }
    }
}
