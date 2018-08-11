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
		int bucketCount = (maxValue - minValue) / bucketSize + 1;
		List<List<Integer>> buckets = new ArrayList<>(bucketCount);
		for (int i = 0; i < bucketCount; i++) {
			buckets.add(new ArrayList<>());
		}

		// Distribute input array values into buckets
		for (int i = 0; i < array.length; i++) {
			buckets.get((array[i] - minValue) / bucketSize).add(array[i]);
		}

		// Sort buckets and place back into input array
		int currentIndex = 0;
		for (int i = 0; i < buckets.size(); i++) {
			Integer[] bucketArray = new Integer[buckets.get(i).size()];
			bucketArray = buckets.get(i).toArray(bucketArray);
			InsertionSort.sort(bucketArray);
			for (int j = 0; j < bucketArray.length; j++) {
				array[currentIndex++] = bucketArray[j];
			}
		}
	}
}
