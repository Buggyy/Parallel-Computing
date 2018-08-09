package java.algoritmes;

/**
 * Maintained and created by:
 * R. Lobato
 */
class InsertionSort {
    /**
     *
     * @param array
     * @param <T>
     */
    static <T extends Comparable<T>> void sort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            T item = array[i];
            int index = i;
            while (index > 0 && array[index - 1].compareTo(item) > 0) {
                array[index] = array[--index];
            }
            array[index] = item;
        }
    }
}