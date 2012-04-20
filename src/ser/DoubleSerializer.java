package ser;

// Big-endian serialization for (64-bit) long integers.
public class DoubleSerializer extends Serializer<Double> {
    public static final DoubleSerializer singleton = new DoubleSerializer();
    private DoubleSerializer() {}

    @Override
    public byte[] serialize(Double x) {
        long l = Double.doubleToLongBits(x);
        return LongSerializer.singleton.serialize(l);
    }

    @Override
    public Double deserialize(byte[] data, int offset, int len) {
        assert len == 8;
        long l = LongSerializer.singleton.deserialize(data, offset, len);
        return Double.longBitsToDouble(l);
    }
}
