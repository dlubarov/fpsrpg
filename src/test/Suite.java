package test;

public class Suite {
    private static final Test[] allTests = new Test[] {
            SerializationTest.singleton
    };

    public static void main(String[] args) {
        for (Test test : allTests)
            test.run();
        System.err.println("Tests completed.");
    }
}
