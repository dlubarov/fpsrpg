package msg;

public abstract class Message {
    public final MessageType type;

    protected Message(MessageType type) {
        this.type = type;
    }

    @Override
    public abstract String toString();
}
