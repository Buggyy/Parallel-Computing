package algoritmes;

import org.junit.Test;
import remote.Server;

import java.util.Arrays;
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

		BubbleSortSerial.sort(values);
	}

	@Test
	public void shouldDoNothingWithOneElementArray() {
		Integer[] values = {42};

		BubbleSortSerial.sort(values);

		assertArrayEquals(new Integer[] {42}, values);
	}

	@Test
	public void shouldSortValues() {

		Integer[] values = { 9, -3, 5, 0, 1};
		Integer[] expectedOrder = { -3, 0, 1, 5, 9};

		BubbleSortSerial.sort(values);
		assertArrayEquals(expectedOrder, values);
	}

}