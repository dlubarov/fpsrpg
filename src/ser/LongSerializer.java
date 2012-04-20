package ser;

// Big-endian serialization for (64-bit) long integers.
public class LongSerializer extends Serializer<Long> {
    public static final LongSerializer singleton = new LongSerializer();
    private LongSerializer() {}

    @Override
    public byte[] serialize(Long object) {
        long x = object;
        return new byte[] {
                (byte) (x >>> 56),
                (byte) (x >>> 48),
                (byte) (x >>> 40),
                (byte) (x >>> 32),
                (byte) (x >>> 24),
                (byte) (x >>> 16),
                (byte) (x >>> 8),
                (byte) (x >>> 0)
        };
    }

    @Override
    public Long deserialize(byte[] data, int offset, int len) {
        assert len == 8;
        return (data[offset + 0] & 0xFFL) << 56
             | (data[offset + 1] & 0xFFL) << 48
             | (data[offset + 2] & 0xFFL) << 40
             | (data[offset + 3] & 0xFFL) << 32
             | (data[offset + 4] & 0xFFL) << 24
             | (data[offset + 5] & 0xFFL) << 16
             | (data[offset + 6] & 0xFFL) << 8
             | (data[offset + 7] & 0xFFL) << 0;
    }
}
