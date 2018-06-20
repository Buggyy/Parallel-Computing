package utilities;

import algorithm.ParallelMergeSort;

/**
 * Created by IntelliJ IDEA
 * User: Kevin
 * Date: 5/17/2017
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
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
