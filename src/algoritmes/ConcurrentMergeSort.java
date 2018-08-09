package algoritmes;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class ConcurrentMergeSort {

    private final ForkJoinPool pool = new ForkJoinPool();
    private final int[] values;

    private static MergeSort mergeSort = new MergeSort();

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

        /**
         *
         * @param values
         * @param low
         * @param high
         * @param threshold
         */
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
                // sequentiele sort
                mergeSort.mergesort(low, high);
                return;
            }

            if (low < high) {
                int middle = low + (high - low) / 2;

                MergeSortTask t1 = new MergeSortTask(values, low, middle, threshold);
                MergeSortTask t2 = new MergeSortTask(values, middle + 1, high, threshold);

                invokeAll(t1, t2);

                mergeSort.merge(low, middle, high);
            }
        }
    }
}
