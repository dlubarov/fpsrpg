package ser;

public abstract class Serializer<T> {
    public abstract byte[] serialize(T object);

    public abstract T deserialize(byte[] data, int offset, int len);

    public final T deserialize(byte[] data) {
        return deserialize(data, 0, data.length);
    }
}
