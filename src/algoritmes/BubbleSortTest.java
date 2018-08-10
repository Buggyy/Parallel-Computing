package algoritmes;

import org.junit.Test;
import remote.Server;

import java.util.logging.Logger;

import static org.junit.Assert.assertArrayEquals;

/**
 * Parallel-Computing
 * AUTHOR: R. Lobato & C. Verra
 * DATE: 8/10/18
 */
public class BubbleSortTest {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	private BubbleSort sorter = new BubbleSort();

	@Test
	public void shouldDoNothingWithEmptyArray() {
		int[] values = {};

		sorter.sort(values);
	}

	@Test
	public void shouldDoNothingWithOneElementArray() {
		int[] values = {42};

		sorter.sort(values);

		assertArrayEquals(new int[] {42}, values);
	}

	@Test
	public void shouldSortValues() {
		long startTime = System.currentTimeMillis();

		int[] values = { 9, -3, 5, 0, 1};
		int[] expectedOrder = { -3, 0, 1, 5, 9};

		sorter.sort(values);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		LOGGER.info("BubbleSort " + elapsedTime + "ms");

		assertArrayEquals(expectedOrder, values);
	}

}