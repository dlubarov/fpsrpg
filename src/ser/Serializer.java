package ser;

public abstract class Serializer<T> {
    public abstract void serialize(T object, ByteSink sink);

    public abstract T deserialize(ByteSource source);

    public final byte[] serialize(T object) {
        ByteSink sink = new ByteSink();
        serialize(object, sink);
        return sink.getContents();
    }

    public final T deserialize(byte[] data) {
        ByteSource source = new ByteSource(data);
        T result = deserialize(source);
        assert source.numBytesRemaining() == 0;
        return result;
    }
}
