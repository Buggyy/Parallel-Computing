package utilities;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Maintained and created by:
 * S. R. Lobato
 * C. Verra
 */

public class Utilities {

    // @Stefan, bron er bij, rename van functie
    // bron: http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
    // en
    // https://stackoverflow.com/questions/47005560/difference-between-fisher-yates-shuffle-and-reservoir-sampling
    // Gebruik maken van fisyer yates
    public static void fisherYatesArrayShuffle(int[] arrayToShuffle) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = arrayToShuffle.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = arrayToShuffle[index];
            arrayToShuffle[index] = arrayToShuffle[i];
            arrayToShuffle[i] = a;
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

    // @Stefan, functie renamed
    public static boolean arrayHasValue(int[] array) {
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

    // @Stefan, renamed func, bron toegevoegd
    // bron: https://stackoverflow.com/questions/19458278/check-if-an-array-is-sorted-return-true-or-false

    public static boolean isArraySorted(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i] < a[i - 1]) return false;
        return true;
    }
    // Stefan onveranderd, alleen stringbuilder renamed
    public static String arrayToString(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i : a) {
            sb.append(String.format("%s ", i));
        }
        return String.join(" ", sb.toString());
    }

    // @Stefan, nieuwe bron, refacored code
    // functie om de array te splitten in opgegeven sizen
    // geinspireerd door @GameDroids: https://stackoverflow.com/questions/27857011/how-to-split-a-string-array-into-small-chunk-arrays-in-java/27857141
    public static int[][] createChunksOfArray(int[] arrayToSplit, int chunkSize) {

        if(chunkSize <= 0) {
            return null;
        }

        int chunks = (int) arrayToSplit.length / chunkSize + (rest > 0 ? 1 : 0);
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
}
