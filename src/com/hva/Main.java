package com.hva;

public class Main {


    public static void main(String[] args) {
        //  Create test Array Object
        Integer[] testArray;

        //  Fill test Array Object
        testArray = GenerateArray(500);


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
