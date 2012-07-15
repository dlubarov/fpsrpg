package ser;

// Big-endian serialization for (32-bit) integers.

public class IntegerSerializer extends Serializer<Integer> {
    public static final IntegerSerializer singleton = new IntegerSerializer();
    private IntegerSerializer() {}

    @Override
    public void serialize(Integer x, ByteSink sink) {
        for (int i = 3; i >= 0; --i)
            sink.add((byte) (x >>> 8 * i));
    }

    @Override
    public Integer deserialize(ByteSource source) {
        int x = 0;
        for (int i = 3; i >= 0; --i)
            x |= (source.read() & 0xFF) << 8 * i;
        return x;
    }
}
