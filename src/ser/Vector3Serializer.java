package ser;

import math.Vector3;

public class Vector3Serializer extends Serializer<Vector3> {
    public static final Vector3Serializer singleton = new Vector3Serializer();
    private Vector3Serializer() {}

    @Override
    public byte[] serialize(Vector3 v) {
        ByteBuilder buf = new ByteBuilder();
        for (double x : v.components())
            buf.addAll(DoubleSerializer.singleton.serialize(x));
        return buf.getContents();
    }

    @Override
    public Vector3 deserialize(byte[] data, int offset, int len) {
        assert len == 24;
        double x = DoubleSerializer.singleton.deserialize(data, offset, 8),
               y = DoubleSerializer.singleton.deserialize(data, offset + 8, 8),
               z = DoubleSerializer.singleton.deserialize(data, offset + 16, 8);
        return new Vector3(x, y, z);
    }
}
