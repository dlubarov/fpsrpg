package ser;

public class ArraySerializer<T> extends Serializer<T[]> {
    private final Serializer<T> elementSerializer;

    public ArraySerializer(Serializer<T> elementSerializer) {
        this.elementSerializer = elementSerializer;
    }

    @Override
    public byte[] serialize(T[] arr) {
        ByteBuilder buf = new ByteBuilder();
        buf.addAll(IntegerSerializer.singleton.serialize(arr.length));
        for (T elem : arr) {
            byte[] elemData = elementSerializer.serialize(elem);
            buf.addAll(IntegerSerializer.singleton.serialize(elemData.length));
            buf.addAll(elemData);
        }
        return buf.getContents();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] deserialize(byte[] data, int offset, int len) {
        int n = IntegerSerializer.singleton.deserialize(data, offset, 4);
        offset += 4;
        T[] arr = (T[]) new Object[n];
        for (int i = 0; i < n; ++i) {
            int elemLen = IntegerSerializer.singleton.deserialize(data, offset, 4);
            offset += 4;
            arr[i] = elementSerializer.deserialize(data, offset, elemLen);
            offset += elemLen;
        }
        return arr;
    }
}
