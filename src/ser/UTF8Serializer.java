package ser;

import java.nio.charset.Charset;

public class UTF8Serializer extends Serializer<String> {
    public static final UTF8Serializer singleton = new UTF8Serializer();
    private UTF8Serializer() {}

    private static final Charset utf8 = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(String s) {
        return s.getBytes(utf8);
    }

    @Override
    public String deserialize(byte[] data, int offset, int len) {
        return new String(data, offset, len, utf8);
    }
}
