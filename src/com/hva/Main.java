package com.hva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) {
        //How many times
        int howManyTimesToRun = 1;

        //  Create test Integer[] Object
        Integer[] testArray;

        Double[] timingsArray = new Double[howManyTimesToRun];

        Double count = 0.0;
        for (int i = 0; i < howManyTimesToRun; i++){

            Integer[] randomArray = GenerateArray(10000000);
//            System.out.println(Arrays.toString(randomArray));

            //Get Start time
            long startTime = System.nanoTime();

            Integer[] sortedArray = BucketSort.sort(randomArray);

            //Get end time
            long duration = System.nanoTime() - startTime;
            count = count + ((double) duration / 1000000000);

//            System.out.println(Arrays.toString(sortedArray));
        }
//        System.out.println(Arrays.toString(timingsArray));
        System.out.format("Estimated measuring time: %f seconds.", count/howManyTimesToRun);

    }

    /**
     * Generate an Array with random Integers
     *
     * @param amountOfIndexes
     * @return Generated Array
     */
    private static Integer[] GenerateArray(int amountOfIndexes) {
        //  Generate Integer[] with given amount of indexes
        Integer[] numbers = new Integer[amountOfIndexes];

        for (int i = 0; i < numbers.length; i++) {
            //  Fill index with a random number till the range of 1000 (can be Integer.MAX_VALUE)
            numbers[i] = (int)(Math.random() * 1000);
        }

        return numbers;
    }
}
