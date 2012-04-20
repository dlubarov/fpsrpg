package util;

public final class ArrayUtil {
    private ArrayUtil() {}

    public static boolean contains(Object[] arr, Object value) {
        for (Object elem : arr)
            if (elem.equals(value))
                return true;
        return false;
    }
}
