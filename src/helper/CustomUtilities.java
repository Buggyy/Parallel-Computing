package helper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class CustomUtilities {

    /**
     * Source: http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
     * & https://stackoverflow.com/questions/47005560/difference-between-fisher-yates-shuffle-and-reservoir-sampling
     * @param arrayToShuffle
     */
    public static void arrayShuffler(int[] arrayToShuffle) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = arrayToShuffle.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = arrayToShuffle[index];
            arrayToShuffle[index] = arrayToShuffle[i];
            arrayToShuffle[i] = a;
        }
    }

    /**
     * 
     * @param ar
     * @param i
     * @param j
     */
    public static void swap(int[] ar, int i, int j) {
        int temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
    }

    /**
	 * Print the array
	 *
	 * @param anArray Array to print ou
	 */
    public static void printArray(Integer[] anArray) {
        System.out.print("Array: ");
        for (int i = 0; i < anArray.length; i++) {
            System.out.print(anArray[i] + " ");
        }
        System.out.println();
    }

    /**
     * Genereer array met gegevens size
     * @param size om array te vullen
     * @return int array met gegeven size
     */
    public static int[] generateArray(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = i;
        }
        return result;
    }

    /**
     *
     * @param array
     * @return
     */
    public static boolean arrayHasValue(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i)
                return false;
        }
        return true;
    }

    /**
     *
     * @param anArray
     * @param value
     */
    public static void addValue(int[] anArray, int value) {
        for (int i = 0; i < anArray.length; i++) {
            anArray[i] += value;
        }
    }


    /**
     * Source: https://stackoverflow.com/questions/19458278/check-if-an-array-is-sorted-return-true-or-false
     * @param a int een array om te kijken of het gesorteerd is
     * @return true, waneer het daadwerkelijk gesorteerd is
     */
    public static boolean isArraySorted(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i] < a[i - 1]) return false;
        return true;
    }

    /**
     *
     * @param a
     * @return
     */
    public static String arrayToString(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i : a) {
            sb.append(String.format("%s ", i));
        }
        return String.join(" ", sb.toString());
    }

    /**
     * @GameDroids: https://stackoverflow.com/questions/27857011/how-to-split-a-string-array-into-small-chunk-arrays-in-java/27857141
     * @param arrayToSplit
     * @param chunkSize
     * @return een multidementionale array met array chunks
     */
    public static int[][] createChunksOfArray(int[] arrayToSplit, int chunkSize) {

        if(chunkSize <= 0) {
            return null;
        }

        int chunks = (int) Math.ceil((double) arrayToSplit.length / chunkSize);

        int[][] chunkedArrays = new int[chunks][];

        for (int i = 0; i < chunks; ++i) {
            int start = i * chunkSize;
            int length = Math.min(arrayToSplit.length - start, chunkSize);

            int[] copyArray = new int[length];
            System.arraycopy(arrayToSplit, start, copyArray, 0, length);
            chunkedArrays[i] = copyArray;
        }

        return chunkedArrays;
    }

	/**
	 * Try to shut exit ExecutorService properly
	 * @param executorService to shut down
	 */
	public static void exitExecutor(ExecutorService executorService) {
		try {
			System.out.println("--------------------------------------");
			System.out.println("Poging afsluiten executor");
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.err.println("Taken onderbroken");
		} finally {
			if (!executorService.isTerminated()) {
				System.err.println("Annuleer niet afgemaakte taken");
			}
			executorService.shutdownNow();
			System.out.println("Shutdown klaar");
		}
	}
}
