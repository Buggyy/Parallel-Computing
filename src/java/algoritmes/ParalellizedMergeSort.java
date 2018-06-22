package java.algoritmes;

import java.helper.Sorter;
/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */

public class ParalellizedMergeSort {

    private static int[] numbers;
    private static int[] helper;
    private static int number;

    private static MergeSort mergeSort = new MergeSort();

    public static void parallelizedMergeSort(int[] values) {
        numbers = values;
        number = values.length;
        helper = new int[number];

        parallelizedMergeSort(0, number - 1, Runtime.getRuntime().availableProcessors());
    }

    public static void parallelizedMergeSort(int low, int high, int threads) {
        if (threads <= 1) {
            mergeSort.mergesort(low, high);
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

                mergeSort.merge(low, middle, high);
            }
        }
    }
}
