package java;

import java.util.Arrays;
/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */

public final class Main {

    private static final int RUNS = 6;
    private static int ARRAY_LENGTH = 1024000;


    public static void main(final String... args) {
        final utilities.Logger eventProfiler = new utilities.Logger(true);

        int[] array = {0, 1, 2, 3, 4, 5, 6, 7};

        System.out.println(Arrays.toString(array));

        int[] array2 = {10, 20, 30, 40};

        System.out.println(Arrays.toString(array2));

        System.arraycopy(array2, 0, array, 0, array2.length);

        System.out.println(Arrays.toString(array));

    }
}