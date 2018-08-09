package algoritmes;

import org.junit.Before;

import remote.Server;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by R. Lobato on 4/10/18.
 */
public class MergeSortTest {

    private final static int SIZE = 7;
    private final static int MAX = 20;
    private int[] numbers;

    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());


    /**
     * setup functie
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        numbers = new int[SIZE];
        Random generator = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = generator.nextInt(MAX);
        }
    }

    @org.testng.annotations.Test
    public void sort() {
        long startTime = System.currentTimeMillis();

        MergeSort sorter = new MergeSort();
        sorter.sort(numbers);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        LOGGER.info("Mergesort " + elapsedTime);

        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                fail("Dit hoort niet");
            }
        }
        assertTrue(true);
    }


    @org.testng.annotations.Test
    public void itWorksRepeatably() {
        for (int i = 0; i < 200; i++) {
            numbers = new int[SIZE];
            Random generator = new Random();
            for (int a = 0; a < numbers.length; a++) {
                numbers[a] = generator.nextInt(MAX);
            }
            MergeSort sorter = new MergeSort();
            sorter.sort(numbers);
            for (int j = 0; j < numbers.length - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    fail("Dit hoort niet");
                }
            }
            assertTrue(true);
        }
    }

    @org.testng.annotations.Test
    public void testStandardSort() {
        long startTime = System.currentTimeMillis();
        Arrays.sort(numbers);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        LOGGER.info("Standaard Java sorteren " + elapsedTime);

        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                fail("Dit hoort niet");
            }
        }
        assertTrue(true);
    }

}
