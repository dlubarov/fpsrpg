package ser;

public class ArraySerializer<T> extends Serializer<T[]> {
    private final Serializer<T> elementSerializer;

    public ArraySerializer(Serializer<T> elementSerializer) {
        this.elementSerializer = elementSerializer;
    }

    @Override
    public void serialize(T[] arr, ByteSink sink) {
        IntegerSerializer.singleton.serialize(arr.length, sink);
        for (T elem : arr)
            elementSerializer.serialize(elem, sink);
    }

    @Override
    public T[] deserialize(ByteSource source) {
        int n = IntegerSerializer.singleton.deserialize(source);
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) new Object[n];
        for (int i = 0; i < n; ++i)
            arr[i] = elementSerializer.deserialize(source);
        return arr;
    }
}
