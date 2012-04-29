package ser;

public final class ByteSource {
    private final byte[] contents;
    private int pos = 0;

    public ByteSource(byte[] contents) {
        this.contents = contents;
    }

    public ByteSource(byte[] data, int offset, int len) {
        contents = new byte[len];
        System.arraycopy(data, offset, contents, 0, len);
    }

    public int numBytesRemaining() {
        return contents.length - pos;
    }

    public byte read() {
        return contents[pos++];
    }

    public byte[] read(int n) {
        byte[] data = new byte[n];
        System.arraycopy(contents, pos, data, 0, n);
        pos += n;
        return data;
    }
}
