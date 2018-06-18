package util;

import algorithm.ParallelMergeSort;

/**
 * Maintained and created by:
 * Chris Verra
 * S. R. Lobato
 */
public class Sorter implements Runnable {

    private int low;
    private int high;
    private int threadCount;

    public Sorter(int low, int high, int threadCount) {
        this.low = low;
        this.high = high;
        this.threadCount = threadCount;
    }

    @Override
    public void run() {
        ParallelMergeSort.parallelMergeSort(low, high, threadCount);
    }
}
