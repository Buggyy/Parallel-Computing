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


	@Test
	public void shouldDoNothingWithEmptyArray() {
		Integer[] values = {};

		BubbleSort.sort(values);
	}

	@Test
	public void shouldDoNothingWithOneElementArray() {
		Integer[] values = {42};

		BubbleSort.sort(values);

		assertArrayEquals(new Integer[] {42}, values);
	}

	@Test
	public void shouldSortValues() {
		long startTime = System.currentTimeMillis();

		Integer[] values = { 9, -3, 5, 0, 1};
		Integer[] expectedOrder = { -3, 0, 1, 5, 9};

		BubbleSort.sort(values);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		LOGGER.info("BubbleSort " + elapsedTime + "ms");

		assertArrayEquals(expectedOrder, values);
	}

}