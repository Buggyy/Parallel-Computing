package algoritmes;

/**
 * Maintained and created by:
 * R. Lobato
 */
public class MergeSort {
    private int[] numbers;
    private int[] helper;

    private int number;

    public void sort(int[] values) {
        this.numbers = values;
        number = values.length;
        this.helper = new int[number];
        mergesort(0, number - 1);
    }

    /**
     *
     * @param low
     * @param high
     */
    protected void mergesort(int low, int high) {
        // kijken of low kleiner is dan high, zo niet, dan is de array gesorteerd
        if (low < high) {
            // pak index van middelsgte element
            int middle = low + (high - low) / 2;
            // sorteer linkerzijde van de array
            mergesort(low, middle);
            // Sorteer rechterzijde van de array
            mergesort(middle + 1, high);
            // beide samenvoegen
            merge(low, middle, high);
        }
    }

    /**
     *
     * @param low
     * @param middle
     * @param high
     */
    protected void merge(int low, int middle, int high) {

        // kopier beide delen in de helper array
        for (int i = low; i <= high; i++) {
            helper[i] = numbers[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        // Kopier de kleinste waarde van een van de kanten terug
        // naar de originele array
        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
        }

        // Kopier de rest van een van de kanten terug in de doel array
        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
        }
        // Omdat we in-place sorteren, sturen we de overige elmenten van de rechterzijde
        // die al op de juiste plek zitten
    }
}
