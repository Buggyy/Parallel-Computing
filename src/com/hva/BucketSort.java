package com.hva;


import java.util.ArrayList;
import java.util.Arrays;
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


    public static double sort(Integer[] array) {
        long startTime = System.nanoTime();

        // Determine minimum and maximum values
        Integer minValue = 0;
        Integer maxValue = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            } else if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }

        //Calculate the amount of buckets, by taking the square root of the amount of items to sort
        int bucketCount = (int)Math.ceil(Math.sqrt(array.length));
        List<List<Integer>> buckets = new ArrayList<List<Integer>>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<Integer>());
        }

        //Calculate the divider
        Integer divider = (int)Math.ceil((maxValue)/buckets.size()+1);

        //Add the values to their respective buckets
        for (Integer anArray : array) {
            buckets.get((int)Math.floor((anArray) / divider)).add(anArray);
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

        long duration = System.nanoTime() - startTime;
        double seconds = ((double) duration / 1000000000);

        //  Calculate estimated measuring time
//        System.out.format("Estimated measuring time: %f seconds.", seconds);

        return seconds;
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