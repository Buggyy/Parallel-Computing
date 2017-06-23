package com.hva;

public class Main {


    public static void main(String[] args) {
        //  Create test Integer[] Object
        Integer[] testArray;

        //  Fill test Array Object
        testArray = GenerateArray(5000000);

        //  Default Bucket Size = 5
        BucketSort.sort(testArray);


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

            //  Fill index with a random number till the range of 100000
            // (Integer.MAX_VALUE throws OutOfMemoryError: Java heap space exception)
            numbers[i] = (int) (Math.random() * 100000);
        }

        return numbers;
    }
}
