import algoritmes.BubbleSort;

import static helper.CustomUtilities.printArray;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */

public final class Main {

    private static final int RUNS = 6;
    private static int ARRAY_LENGTH = 1024000;
	private static int TEST_ARRAY[] = {64, 34, 25, 12, 22, 11, 90};


    public static void main(final String... args) {

        // START- BubbleSort
		System.out.println("---- BubbleSort ----");
		System.out.println();
		BubbleSort ob = new BubbleSort();
		ob.sort(TEST_ARRAY);

		printArray(TEST_ARRAY);
		// END- BubbleSort

		// START- algorithm
		// Code..
		// END- algorithm
	}
}