package mergesort;
import utilities.Sorter;

/**
 * Maintained and created by:
 * S. R. Lobato
 * C. Verra
 */

// @Stefan, class rename
public class ParalellizedMergeSort {

    private static int[] numbers;
    private static int[] helper;
    private static int number;

    public static void parallelizedMergeSort(int[] values) {
        numbers = values;
        number = values.length;
        helper = new int[number];

        parallelizedMergeSort(0, number - 1, Runtime.getRuntime().availableProcessors());
    }

    public static void parallelizedMergeSort(int low, int high, int threads) {
        if (threads <= 1) {
            mergeSort(low, high); //normal mergesort
        } else if (number > 2) {
            if (low < high) {
                int middle = low + (high - low) / 2;

                Thread leftThread = new Thread(new Sorter(low, middle, threads / 2));
                Thread rightThread = new Thread(new Sorter(middle + 1, high, threads / 2));

                leftThread.start();
                rightThread.start();

                try {
                    leftThread.join();
                    rightThread.join();
                } catch (InterruptedException ignored) {

                }

                merge(low, middle, high);
            }
        }
    }

    protected static void mergeSort(int low, int high) {
        // check if low is smaller than high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            mergeSort(low, middle);
            // Sort the right side of the array
            mergeSort(middle + 1, high);
            // Combine them both
            merge(low, middle, high);
        }
    }

    protected static void merge(int low, int middle, int high) {
        // Copy both parts into the helper array
        System.arraycopy(numbers, low, helper, low, high + 1 - low);

        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array

        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
        }
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
        }
    }
}
