package algoritmes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static helper.CustomUtilities.exitExecutor;

/**
 * Parallel-Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class BucketSort {

    private static final int DEFAULT_BUCKET_SIZE = 5;
    private static int currentIndex = 0;

    public static void sort(Integer[] array) {
        sort(array, DEFAULT_BUCKET_SIZE);
    }
    static void sort(Integer[] arrayToSort, int bucketSize) {


        System.out.println("Doe BucketSort met " + arrayToSort.length + " integers \n");

        //  Start timer
        final long startTime = System.nanoTime();

        if (arrayToSort.length == 0) {
            return;
        }

        // Bepaal min en max waarden
        Integer minValue = arrayToSort[0];
        Integer  maxValue= arrayToSort[0];
        for (int i = 1; i < arrayToSort.length; i++) {
            if (arrayToSort[i] < minValue) {
                minValue = arrayToSort[i];
            } else if (arrayToSort[i] > maxValue) {
                maxValue = arrayToSort[i];
            }
        }


        // Maak een executor service met een thread pool welke nieuwe threads maakt wanneer nodig,
        // indien mogelijk hergebruik toepassen van threads
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

            // input over buckets distribueren
            for (Integer anArrayElement : arrayToSort) {
                buckets.get((anArrayElement - finalMinValue) / bucketSize).add(anArrayElement);

            }
            // Sorteer buckets en stop het terug in input array
            //  Loop door de content van elke bucket
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


		exitExecutor(executorService);

		final long duration = System.nanoTime() - startTime;
        final double seconds = ((double) duration / 1000000000);

        // Bereken geschatte measure tijd
        System.out.format("Schatting Meettijd: %f secondes. \n\n\n", seconds);

    }

    /**
     * Synchroniseer de incrementatie van de huidige index
     */
    static synchronized void incrementSync() {
        currentIndex = currentIndex + 1;
    }
}