package test;

import java.util.*;

public abstract class Test {
    private static final boolean EXIT_ON_FAILURE = true;

    protected final Random rng;

    protected Test() {
        rng = new Random(getClass().getName().hashCode());
    }

    public void setUp() {}
    public void tearDown() {}

    protected abstract void test();

    public final void run() {
        setUp();
        test();
        tearDown();
    }

    private static void fail(String msg, Object... args) {
        StackTraceElement[] elems = new Exception().getStackTrace();
        for (StackTraceElement e : elems)
            if (!e.getClassName().equals(Test.class.getName())) {
                System.err.printf("Failed: %s.%s (line %d).\n",
                        e.getClassName(), e.getMethodName(), e.getLineNumber());
                if (msg != null)
                    System.err.printf("    %s\n", String.format(msg, args));
                if (EXIT_ON_FAILURE)
                    System.exit(1);
                return;
            }
        throw new RuntimeException("Failed to print the test failure. Epic fail!");
    }

    private static void fail() {
        fail(null);
    }

    public void assertTrue(boolean b) {
        if (!b)
            fail();
    }

    public void assertFalse(boolean b) {
        if (b)
            fail();
    }

    public void assertEqual(Object a, Object b) {
        if (!a.equals(b))
            fail("'%s' is not equal to '%s'.", a, b);
    }

    public void assertNotEqual(Object a, Object b) {
        if (a.equals(b))
            fail("'%s' is equal to '%s'.", a, b);
    }

    public void assertArraysEqual(Object[] a, Object[] b) {
        if (!Arrays.equals(a, b))
            fail("%s is not equal to %s.", Arrays.toString(a), Arrays.toString(b));
    }

    public void assertArraysNotEqual(Object[] a, Object[] b) {
        if (Arrays.equals(a, b))
            fail("%s is equal to %s.", Arrays.toString(a), Arrays.toString(b));
    }
}
