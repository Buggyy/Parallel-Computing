package algoritmes;

import remote.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static helper.CustomUtilities.exitExecutor;

/**
 * Parallel-Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class BubbleSort {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	private static int currentIndex = 0;


	public static void sort(Integer arrayToSort[]) {
		LOGGER.info("BubbleSort Started");

		//  Start timer
		final long startTime = System.nanoTime();

		//	Return if empty
		if (arrayToSort.length == 0) {
			return;
		}

		int n = arrayToSort.length;

		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.execute(() -> {
			//	Iterate over array
			for (int i = 0; i < n - 1; i++)
				//	Iterate from i and further
				for (int j = 0; j < n - i - 1; j++)
					//	Swap left with right, if necessary
					if (arrayToSort[currentIndex] > arrayToSort[currentIndex + 1]) {
						// swap temp and arrayToSort[i]
						int temp = arrayToSort[currentIndex];
						arrayToSort[currentIndex] = arrayToSort[currentIndex + 1];
						arrayToSort[currentIndex + 1] = temp;
						incrementSync();
					}

		});

		exitExecutor(executorService);

		final long duration = System.nanoTime() - startTime;
		final double seconds = ((double) duration / 1000000000);

		LOGGER.info("BubbleSort Ended: " + seconds + "ms");
	}

	/**
	 * Synchroniseer de incrementatie van de huidige index
	 */
	static synchronized void incrementSync() {
		currentIndex = currentIndex + 1;
	}
}
