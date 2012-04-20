package msg;

import ser.*;

public class NewAccountMessage extends Message {
    public final String username, password;

    public NewAccountMessage(String username, String password) {
        super(MessageType.NEW_ACCOUNT);
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
        public byte[] serialize(NewAccountMessage msg) {
            ByteBuilder buf = new ByteBuilder();
            buf.add((byte) MessageType.NEW_ACCOUNT.ordinal());
            byte[] usernameData = UTF8Serializer.singleton.serialize(msg.username),
                   passwordData = UTF8Serializer.singleton.serialize(msg.password);
            buf.addAll(IntegerSerializer.singleton.serialize(usernameData.length));
            buf.addAll(usernameData);
            buf.addAll(IntegerSerializer.singleton.serialize(passwordData.length));
            buf.addAll(passwordData);
            return buf.getContents();
        }

        @Override
        public NewAccountMessage deserialize(byte[] data, int offset, int len) {
            int usernameLen = IntegerSerializer.singleton.deserialize(data, offset, 4);
            offset += 4;
            String username = UTF8Serializer.singleton.deserialize(data, offset, usernameLen);
            offset += usernameLen;
            int passwordLen = IntegerSerializer.singleton.deserialize(data, offset, 4);
            offset += 4;
            String password = UTF8Serializer.singleton.deserialize(data, offset, passwordLen);
            offset += passwordLen;
            return new NewAccountMessage(username, password);
        }
    }
}
