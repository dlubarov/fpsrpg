package ser;

import math.Vector3;

public class Vector3Serializer extends Serializer<Vector3> {
    public static final Vector3Serializer singleton = new Vector3Serializer();
    private Vector3Serializer() {}

    @Override
    public void serialize(Vector3 v, ByteSink sink) {
        for (double x : v.components())
            sink.addAll(DoubleSerializer.singleton.serialize(x));
    }

    @Override
    public Vector3 deserialize(ByteSource source) {
        return new Vector3(
                DoubleSerializer.singleton.deserialize(source),
                DoubleSerializer.singleton.deserialize(source),
                DoubleSerializer.singleton.deserialize(source)
        );
    }
}
