package com.hva;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.R. Lobato on 5/10/17.
 */
class BucketSort {
    private static final int DEFAULT_BUCKET_SIZE = 5;

    static void sort(Integer[] array) {
        sort(array, DEFAULT_BUCKET_SIZE);
    }

    private static void sort(Integer[] array, int bucketSize) {
        //  Start timer
        final long startTime = System.nanoTime();

        if (array.length == 0) {
            return;
        }

        // Determine minimum and maximum values
        Integer minValue = array[0];
        Integer maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            } else if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }

        // Initialise buckets
        //  TODO One Thread divides the buckets over other threads
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Distribute input array values into buckets
        for (Integer anArray : array) {
            buckets.get((anArray - minValue) / bucketSize).add(anArray);
        }

        // Sort buckets and place back into input array
        int currentIndex = 0;
        for (List<Integer> bucket : buckets) {
            Integer[] bucketArray = new Integer[bucket.size()];
            bucketArray = bucket.toArray(bucketArray);
            InsertionSort.sort(bucketArray);
            for (Integer aBucketArray : bucketArray) {
                array[currentIndex++] = aBucketArray;
            }
        }

        final long duration = System.nanoTime() - startTime;
        final double seconds = ((double) duration / 1000000000);

        //  Calculate estimated measuring time
        System.out.format("Estimated measuring time: %f seconds.", seconds);
    }
}