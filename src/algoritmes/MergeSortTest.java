package algoritmes;


import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;
import remote.Server;

import java.util.Random;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Parallel-Computing
 * AUTHOR: R. Lobato & C. Verra
 * DATE: 4/10/18
 */
public class MergeSortTest {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	private final static int SIZE = 7;
	private final static int MAX = 20;
	private int[] numbers;

	private MergeSort sorter = new MergeSort();

	/**
	 * setup functie
	 */
	@BeforeEach
	public void setUp() {
		numbers = new int[SIZE];
		Random generator = new Random();
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = generator.nextInt(MAX);
		}
	}

	@Test
	public void testMergeSort_NormalSort() {
		MergeSort mergeSort = new MergeSort();
		int[] arrayToSort = new int[]{4, 2, 1, 1, 6, 4, 5, 3};

		mergeSort.sort(arrayToSort);

		int[] expected = new int[]{1, 1, 2, 3, 4, 4, 5, 6};

		assertArrayEquals(expected, arrayToSort);
	}


	@Test
	public void testMergeSort_itWorksRepeatably() {
		for (int i = 0; i < 200; i++) {
			numbers = new int[SIZE];
			Random generator = new Random();
			for (int a = 0; a < numbers.length; a++) {
				numbers[a] = generator.nextInt(MAX);
			}

			sorter.sort(numbers);

			for (int j = 0; j < numbers.length - 1; j++) {
				if (numbers[j] > numbers[j + 1]) {
					fail("Dit hoort niet");
				}
			}
			assertTrue(true);
		}
	}
}
