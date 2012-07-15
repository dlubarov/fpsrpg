package msg.s2c;

import ser.*;

public class NewAccountErrorMessage extends ServerMessage {
    public final Cause cause;

    public NewAccountErrorMessage(Cause cause) {
        super(MySerializer.singleton);
        this.cause = cause;
    }

    @Override
    public String toString() {
        return cause.toString();
    }

    public static enum Cause {
        USERNAME_SHORT, USERNAME_LONG,
        PASSWORD_SHORT, PASSWORD_LONG,
        USERNAME_TAKEN
    }

    public static class MySerializer extends Serializer<NewAccountErrorMessage> {
        public static final MySerializer singleton = new MySerializer();
        private MySerializer() {}

        @Override
        public void serialize(NewAccountErrorMessage msg, ByteSink sink) {
            IntegerSerializer.singleton.serialize(msg.cause.ordinal(), sink);
        }

        @Override
        public NewAccountErrorMessage deserialize(ByteSource source) {
            int causeOrd = IntegerSerializer.singleton.deserialize(source);
            try {
                Cause cause = Cause.values()[causeOrd];
                return new NewAccountErrorMessage(cause);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.printf("Bad new account error cause: %d.\n", causeOrd);
                return null;
            }
        }
    }
}
