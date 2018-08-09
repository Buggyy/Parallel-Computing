package helper;

import algoritmes.ParalellizedMergeSort;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class Sorter implements Runnable {

    private int low;
    private int high;
    private int threads;


    /**
     *
     * @param low
     * @param high
     * @param threads
     */
    public Sorter(int low, int high, int threads) {
        this.low = low;
        this.high = high;
        this.threads = threads;
    }

    @Override
    public void run() {
		ParalellizedMergeSort.parallelizedMergeSort(low, high, threads);
    }
}
