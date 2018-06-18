import utilities.EventProfiler;
import java.util.Arrays;

/**
 * Maintained and created by:
 * Chris Verra
 * S. R. Lobato
 */

public final class Main {

    private static final int RUNS = 6;
    private static int ARRAY_LENGTH = 1024000;


    public static void main(final String... args) {
        final EventProfiler eventProfiler = new EventProfiler(true);

        int[] array = {0, 1, 2, 3, 4, 5, 6, 7};

        System.out.println(Arrays.toString(array));

        int[] array2 = {10, 20, 30, 40};

        System.out.println(Arrays.toString(array2));

        System.arraycopy(array2, 0, array, 0, array2.length);

        System.out.println(Arrays.toString(array));

//        for (int i = 0; i < RUNS; i++) {
//            int[] array = Utilities.fillArray(ARRAY_LENGTH);
//
//            Utilities.shuffleArray(array);
//
//            final ConcurrentMergeSort concurrentMergeSort = new ConcurrentMergeSort(array);
//
//            eventProfiler.start();
//            concurrentMergeSort.sort();
//            eventProfiler.log("Concurrent merge sort [" + ARRAY_LENGTH + "] sort done");
//
//            System.out.println(Utilities.isSorted(array));
//
//            ARRAY_LENGTH *= 2;
//        }
    }
}