package algoritmes;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

/**
 * Parallel-Computing
 * AUTHOR: R. Lobato & C. Verra
 * DATE: 8/11/18
 */
public class SerialBucketSortTest {

	private final static int SIZE = 7;
	private final static int MAX = 20;
	private Integer[] arrayToSort;

	@Before
	public void setUp() {
		arrayToSort = new Integer[SIZE];
		Random generator = new Random();
		for (int i = 0; i < arrayToSort.length; i++) {
			arrayToSort[i] = generator.nextInt(MAX);
		}
	}

	@Test
	public void testBucketSort_ShouldOutputSorted_BucketSizeFive() {
		Integer[] arrayToSort = new Integer[]{4, 2, 1, 1, 6, 4, 5, 3};

		SerialBucketSort.sort(arrayToSort, 5);

		Integer[] expected = new Integer[]{1, 1, 2, 3, 4, 4, 5, 6};

		assertArrayEquals(expected, arrayToSort);
	}
}