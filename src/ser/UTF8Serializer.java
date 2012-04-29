package ser;

import java.nio.charset.Charset;

public class UTF8Serializer extends Serializer<String> {
    public static final UTF8Serializer singleton = new UTF8Serializer();
    private UTF8Serializer() {}

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public void serialize(String s, ByteSink sink) {
        byte[] data = s.getBytes(UTF8);
        IntegerSerializer.singleton.serialize(data.length, sink);
        sink.addAll(data);
    }

    @Override
    public String deserialize(ByteSource source) {
        int len = IntegerSerializer.singleton.deserialize(source);
        byte[] data = source.read(len);
        return new String(data, UTF8);
    }
}
