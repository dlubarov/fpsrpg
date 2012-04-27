package test;

import math.Vector3;
import ser.*;

public class SerializationTest extends Test {
    public static final SerializationTest singleton = new SerializationTest();
    private SerializationTest() {}

    private char randomChar() {
        for (;;) {
            char c = (char) rng.nextInt(Character.MAX_VALUE);
            if (Character.isDefined(c) && !(Character.isLowSurrogate(c) || Character.isHighSurrogate(c)))
                return c;
        }
    }

    private String randomString(double expectedLen) {
        StringBuilder sb = new StringBuilder();
        double pContinue = expectedLen / (1 + expectedLen); // math...
        while (rng.nextDouble() < pContinue)
            sb.append(randomChar());
        return sb.toString();
    }

    private double randomDouble() {
        return (rng.nextDouble() - .5) * 999999;
    }

    private Vector3 randomVector3() {
        return new Vector3(randomDouble(), randomDouble(), randomDouble());
    }

    private void testIntegerSerialization() {
        for (int i = 0; i < 20; ++i) {
            int n = rng.nextInt();
            byte[] nData = IntegerSerializer.singleton.serialize(n);
            assertEqual(nData.length, 4);
            assertEqual(n, IntegerSerializer.singleton.deserialize(nData));
        }
    }

    private void testLongSerialization() {
        for (int i = 0; i < 20; ++i) {
            long n = rng.nextLong();
            byte[] nData = LongSerializer.singleton.serialize(n);
            assertEqual(nData.length, 8);
            assertEqual(n, LongSerializer.singleton.deserialize(nData));
        }
    }

    private void testDoubleSerialization() {
        for (int i = 0; i < 20; ++i) {
            double x = randomDouble();
            byte[] xData = DoubleSerializer.singleton.serialize(x);
            assertEqual(xData.length, 8);
            assertEqual(x, DoubleSerializer.singleton.deserialize(xData));
        }
    }

    private void testUTF8Serialization() {
        for (int i = 0; i < 10; ++i) {
            String s = randomString(5.0);
            byte[] sData = UTF8Serializer.singleton.serialize(s);
            assertEqual(s, UTF8Serializer.singleton.deserialize(sData));
        }
    }

    private void testVectorSerialization() {
        for (int i = 0; i < 10; ++i) {
            Vector3 v = randomVector3();
            byte[] vData = Vector3Serializer.singleton.serialize(v);
            assert vData.length == 24;
            assertEqual(v, Vector3Serializer.singleton.deserialize(vData));
        }
    }

    private void testArraySerialization() {
        ArraySerializer<Vector3> vector3ArraySerializer =
                new ArraySerializer<Vector3>(Vector3Serializer.singleton);
        ArraySerializer<String> stringArraySerializer =
                new ArraySerializer<String>(UTF8Serializer.singleton);
        for (int i = 0; i < 10; ++i) {
            int len = rng.nextInt(8);
            Vector3[] vs = new Vector3[len];
            String[] ss = new String[len];
            for (int j = 0; j < len; ++j) {
                vs[j] = randomVector3();
                ss[j] = randomString(5.0);
            }
            byte[] vsData = vector3ArraySerializer.serialize(vs),
                   ssData = stringArraySerializer.serialize(ss);
            assertArraysEqual(vs, vector3ArraySerializer.deserialize(vsData));
            assertArraysEqual(ss, stringArraySerializer.deserialize(ssData));
        }
    }

    @Override
    protected void test() {
        testIntegerSerialization();
        testLongSerialization();
        testDoubleSerialization();
        testUTF8Serialization();
        testVectorSerialization();
        testArraySerialization();
    }
}
