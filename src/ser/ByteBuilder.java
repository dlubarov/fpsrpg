package ser;

public final class ByteBuilder {
    private byte[] data;
    private int len;

    public ByteBuilder() {
        data = new byte[4];
        len = 0;
    }

    private void resize(int newCapacity) {
        byte[] newData = new byte[newCapacity];
        System.arraycopy(data, 0, newData, 0, len);
        data = newData;
    }

    public void add(byte b) {
        if (len == data.length)
            resize(2 * len);
        data[len++] = b;
    }

    public void addAll(byte[] bytes) {
        for (byte b : bytes)
            add(b);
    }

    public byte[] getContents() {
        byte[] result = new byte[len];
        System.arraycopy(data, 0, result, 0, len);
        return result;
    }
}
