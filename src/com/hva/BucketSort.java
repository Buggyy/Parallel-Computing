package com.hva;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Maintained and created by:
 * S.R. Lobato
 * D. Stern
 * J. Steenmans
 */
class BucketSort {


    private static final int DEFAULT_BUCKET_SIZE = 5;
    private static int currentIndex = 0;


    static void sort(Integer[] array) {
        sort(array, DEFAULT_BUCKET_SIZE);
    }

    static void sort(Integer[] arrayToSort, int bucketSize) {


        System.out.println("Performing BucketSort on " + arrayToSort.length + " integers \n");

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

        //  Create an executor service with  a thread pool that creates new threads as needed,
        //  but will reuse previously constructed threads when they are available
        ExecutorService executorService = Executors.newCachedThreadPool();

        Integer finalMinValue = minValue;
        Integer finalMaxValue = maxValue;

        executorService.execute(() -> {
            // Initialise buckets
            int bucketCount = (finalMaxValue - finalMinValue) / bucketSize + 1;
            List<List<Integer>> buckets = new ArrayList<>(bucketCount);

            for (int i = 0; i < bucketCount; i++) {
                buckets.add(new ArrayList<>());
            }


            // Distribute input array values into buckets
            for (Integer anArrayElement : arrayToSort) {
                buckets.get((anArrayElement - finalMinValue) / bucketSize).add(anArrayElement);

            }

            //  Sort buckets and place back into input array
            //  Loop through the contents of each bucket
            for (List<Integer> bucket : buckets) {


                Integer[] bucketArray = new Integer[bucket.size()];

                bucketArray = bucket.toArray(bucketArray);

                InsertionSort.sort(bucketArray);

                for (Integer aBucketArray : bucketArray) {
                    arrayToSort[currentIndex] = aBucketArray;
                    incrementSync();
                }
            }
        });


        try {
            System.out.println("--------------------------------------");
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
        System.out.format("Estimated measuring time: %f seconds. \n\n\n", seconds);

    }

    /**
     * Synchronize the incrementation of the current index
     */
    static synchronized void incrementSync() {
        currentIndex = currentIndex + 1;
    }


}