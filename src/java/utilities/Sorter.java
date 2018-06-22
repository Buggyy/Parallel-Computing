package java.utilities;

/**
 * Maintained and created by:
 * R. Lobato
 * C. Verra
 */
// @Stefan, niks aangepast
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
        mergesort.ParalellizedMergeSort.parallelizedMergeSort(low, high, threads);
    }
}
