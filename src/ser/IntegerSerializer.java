package ser;

// Big-endian serialization for (32-bit) integers.
public class IntegerSerializer extends Serializer<Integer> {
    public static final IntegerSerializer singleton = new IntegerSerializer();
    private IntegerSerializer() {}

    @Override
    public byte[] serialize(Integer object) {
        int x = object;
        return new byte[] {
                (byte) (x >>> 24),
                (byte) (x >>> 16),
                (byte) (x >>> 8),
                (byte) (x >>> 0)
        };
    }

    @Override
    public Integer deserialize(byte[] data, int offset, int len) {
        assert len == 4;
        return (data[offset + 0] & 0xFF) << 24
             | (data[offset + 1] & 0xFF) << 16
             | (data[offset + 2] & 0xFF) << 8
             | (data[offset + 3] & 0xFF) << 0;
    }
}
