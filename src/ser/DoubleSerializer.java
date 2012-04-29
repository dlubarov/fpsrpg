package ser;

// Serialization for (64-bit) double precision floats.

public class DoubleSerializer extends Serializer<Double> {
    public static final DoubleSerializer singleton = new DoubleSerializer();
    private DoubleSerializer() {}

    @Override
    public void serialize(Double x, ByteSink sink) {
        long l = Double.doubleToLongBits(x);
        LongSerializer.singleton.serialize(l, sink);
    }

    @Override
    public Double deserialize(ByteSource source) {
        long l = LongSerializer.singleton.deserialize(source);
        return Double.longBitsToDouble(l);
    }
}
