package msg.c2s;

import ser.*;

// A new account request.

public class NewAccountMessage extends ClientMessage {
    public final String username, password;

    public NewAccountMessage(String username, String password) {
        super(MySerializer.singleton);
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("NewAccount(%s, %s)", username, password);
    }

    public static class MySerializer extends Serializer<NewAccountMessage> {
        public static final MySerializer singleton = new MySerializer();
        private MySerializer() {}

        @Override
        public void serialize(NewAccountMessage msg, ByteSink sink) {
            UTF8Serializer.singleton.serialize(msg.username, sink);
            UTF8Serializer.singleton.serialize(msg.password, sink);
        }

        @Override
        public NewAccountMessage deserialize(ByteSource source) {
            String username = UTF8Serializer.singleton.deserialize(source),
                   password = UTF8Serializer.singleton.deserialize(source);
            return new NewAccountMessage(username, password);
        }
    }
}
