package msg;

public abstract class Message {
    public final MessageType type;

    protected Message(MessageType type) {
        this.type = type;
    }

    public final byte[] serialize() {
        return MessageSerializer.singleton.serialize(this);
    }

    @Override
    public abstract String toString();
}
