package utilities;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Maintained and created by:
 * S. R. Lobato
 * C. Verra
 */

public class Utilities {
    // source: http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
    // Using Fisher Yates
    public static void fisherYatesArrayShuffle(int[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap -> RKO: use swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static void swap(int[] ar, int i, int j) {
        int temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
    }

    public static void printArray(int[] anArray) {
        System.out.print("Array: ");
        for (int i = 0; i < anArray.length; i++) {
            System.out.print(anArray[i] + " ");
        }
        System.out.println();
    }

    public static int[] fillArray(int amount) {
        int[] result = new int[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = i;
        }
        return result;
    }

    public static boolean isFilledArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i)
                return false;
        }
        return true;
    }

    public static void addValue(int[] anArray, int value) {
        for (int i = 0; i < anArray.length; i++) {
            anArray[i] += value;
        }
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i] < a[i - 1]) return false;
        return true;
    }

    public static String arrayToString(int[] a) {
        StringBuilder builder = new StringBuilder();
        for (int i : a) {
            builder.append(String.format("%s ", i));
        }
        return String.join(" ", builder.toString());
    }

    //https://gist.github.com/lesleh/7724554
    public static int[][] chunkArray(int[] array, int chunkSize) {
        int numOfChunks = (int) Math.ceil((double) array.length / chunkSize);
        int[][] output = new int[numOfChunks][];

        for (int i = 0; i < numOfChunks; ++i) {
            int start = i * chunkSize;
            int length = Math.min(array.length - start, chunkSize);

            int[] temp = new int[length];
            System.arraycopy(array, start, temp, 0, length);
            output[i] = temp;
        }

        return output;
    }
}
