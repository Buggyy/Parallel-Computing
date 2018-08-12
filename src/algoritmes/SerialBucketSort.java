package algoritmes;

import java.util.ArrayList;
import java.util.List;

/**
 * Parallel-Computing
 * AUTHOR: R. Lobato & C. Verra
 * DATE: 8/11/18
 */
public class SerialBucketSort {
	private static final int DEFAULT_BUCKET_SIZE = 5;

	public static void sort(Integer[] array) {
		sort(array, DEFAULT_BUCKET_SIZE);
	}
	public static void sort(Integer[] array, int bucketSize) {
		if (array.length == 0) {
			return;
		}

		// Bepaal minimale en maximale waarden
		Integer minValue = array[0];
		Integer maxValue = array[0];
		for (int i = 1; i < array.length; i++)
			if (array[i] < minValue) {
				minValue = array[i];
			} else if (array[i] > maxValue) {
				maxValue = array[i];
			}

		// Initialiseer Buckets
		int bucketCount = (maxValue - minValue) / bucketSize + 1;
		List<List<Integer>> buckets = new ArrayList<>(bucketCount);
		for (int i = 0; i < bucketCount; i++) {
			buckets.add(new ArrayList<>());
		}

		// Verdeel input array-waardes over buckets
		for (Integer anArray : array) {
			buckets.get((anArray - minValue) / bucketSize).add(anArray);
		}

		// Sorteer buckets en plaats deze terug in de invoerarray
		int currentIndex = 0;
		for (List<Integer> bucket : buckets) {
			Integer[] bucketArray = new Integer[bucket.size()];
			bucketArray = bucket.toArray(bucketArray);
			InsertionSort.sort(bucketArray);
			for (Integer aBucketArray : bucketArray) {
				array[currentIndex++] = aBucketArray;
			}
		}
	}
}
