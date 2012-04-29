package ser;

// Big-endian serialization for (64-bit) long integers.

public class LongSerializer extends Serializer<Long> {
    public static final LongSerializer singleton = new LongSerializer();
    private LongSerializer() {}

    @Override
    public void serialize(Long object, ByteSink sink) {
        long x = object;
        for (int i = 7; i >= 0; --i)
            sink.add((byte) (x >>> 8 * i));
    }

    @Override
    public Long deserialize(ByteSource source) {
        long x = 0;
        for (int i = 7; i >= 0; --i)
            x |= (source.read() & 0xFFL) << 8 * i;
        return x;
    }
}
