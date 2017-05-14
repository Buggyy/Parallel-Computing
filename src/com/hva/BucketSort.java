package com.hva;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by S.R. Lobato on 5/10/17.
 */
public class BucketSort implements Runnable {


    private static final int DEFAULT_BUCKET_SIZE = 5;
    private static int currentIndex = 0;

    static void sort(Integer[] array) {
        sort(array, DEFAULT_BUCKET_SIZE);
    }

    private static void sort(Integer[] arrayToSort, int bucketSize) {


        //  Start timer
        final long startTime = System.nanoTime();

        if (arrayToSort.length == 0) {
            return;
        }

        // Determine minimum and maximum values
        Integer minValue = arrayToSort[0];
        Integer maxValue = arrayToSort[0];
        for (int i = 1; i < arrayToSort.length; i++) {
            if (arrayToSort[i] < minValue) {
                minValue = arrayToSort[i];
            } else if (arrayToSort[i] > maxValue) {
                maxValue = arrayToSort[i];
            }
        }

        // Initialise buckets
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }


        // Distribute input array values into buckets
        // TODO in separate threads
        for (Integer anArrayElement : arrayToSort) {

            buckets.get((anArrayElement - minValue) / bucketSize).add(anArrayElement);

        }

        //  TODO nThreads should be automatically calculated (.e.g. list.size() / amountDivided + 1)
        //  Create an executor service backed by a thread-pool of size 5
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //  Sort buckets and place back into input array
        //  Loop through the contents of each bucket
        for (List<Integer> bucket : buckets) {

            executorService.execute(() -> {

                Integer[] bucketArray = new Integer[bucket.size()];

                bucketArray = bucket.toArray(bucketArray);

                InsertionSort.sort(bucketArray);

                for (Integer aBucketArray : bucketArray) {
                    arrayToSort[currentIndex] = aBucketArray;
                    incrementSync();
                }

            });


        }

        try {
            System.out.println("Attempt to shutdown executor");
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Tasks interrupted");
        } finally {
            if (!executorService.isTerminated()) {
                System.err.println("Cancel non-finished tasks");
            }
            executorService.shutdownNow();
            System.out.println("Shutdown finished");
        }


        final long duration = System.nanoTime() - startTime;
        final double seconds = ((double) duration / 1000000000);

        //  Calculate estimated measuring time
        System.out.format("Estimated measuring time: %f seconds.", seconds);
    }

    /**
     * Synchronize the incrementation of the current index
     */
    static synchronized void incrementSync() {
        currentIndex = currentIndex + 1;
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Command = " + currentIndex);
        incrementSync();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " End.");

    }
}