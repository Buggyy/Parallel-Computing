package mergesort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Maintained and created by:
 * Chris Verra
 * S. R. Lobato
 */
public class ConcurrentMergeSort {

    private final ForkJoinPool pool = new ForkJoinPool();
    private final int[] values;

    public ConcurrentMergeSort(int[] values) {
        this.values = values;
    }

    public void sort() {
        final int threshold = values.length / Runtime.getRuntime().availableProcessors();

        MergeSortTask mergeSortTask = new MergeSortTask(values, 0, values.length - 1, threshold);
        pool.invoke(mergeSortTask);
    }

    private class MergeSortTask extends RecursiveAction {

        private final int[] values;
        private final int[] helper;

        private final int low;
        private final int high;
        private final int threshold;

        private MergeSortTask(int[] values, int low, int high, int threshold) {
            this.values = values;
            this.helper = new int[values.length];
            this.low = low;
            this.high = high;
            this.threshold = threshold;
        }

        @Override
        protected void compute() {
            if (high - low <= threshold) {
                // sequential sort
                mergeSort(low, high);
//                Arrays.sort(values, low, high);
                return;
            }

            if (low < high) {
                int middle = low + (high - low) / 2;

                MergeSortTask t1 = new MergeSortTask(values, low, middle, threshold);
                MergeSortTask t2 = new MergeSortTask(values, middle + 1, high, threshold);

                invokeAll(t1, t2);

                merge(low, middle, high);
            }
        }

        private void mergeSort(int low, int high) {
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

        private void merge(int low, int middle, int high) {
            // Copy both parts into the helper array
            System.arraycopy(values, low, helper, low, high + 1 - low);

            int i = low;
            int j = middle + 1;
            int k = low;
            // Copy the smallest values from either the left or the right side back
            // to the original array

            while (i <= middle && j <= high) {
                if (helper[i] <= helper[j]) {
                    values[k] = helper[i];
                    i++;
                } else {
                    values[k] = helper[j];
                    j++;
                }
                k++;
            }
            // Copy the rest of the left side of the array into the target array
            while (i <= middle) {
                values[k] = helper[i];
                k++;
                i++;
            }
        }
    }
}
