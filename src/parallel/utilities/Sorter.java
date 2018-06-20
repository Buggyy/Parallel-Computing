package utilities;

import algorithm.ParallelMergeSort;

/**
 * Maintained and created by:
 * S. R. Lobato
 * C. Verra
 */
// @Stefan, niks aangepast
public class Sorter implements Runnable {

    private int low;
    private int high;
    private int threads;

    public Sorter(int low, int high, int threads) {
        this.low = low;
        this.high = high;
        this.threads = threads;
    }

    @Override
    public void run() {
        ParallelMergeSort.parallelMergeSort(low, high, threads);
    }
}
