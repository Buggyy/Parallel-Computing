package com.hva;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;
import java.util.Scanner;

import static com.hva.BucketSortfromQueue.ACTIVEMQ_URL;

/**
 * The ActiveMQ Producer Class
 * <p>
 * Maintained and created by:
 * S. R. Lobato
 */
public class Producer {
    //    Queue name of Producer
    private static String subject = "testQueue1";

    public static void main(String[] args) throws JMSException {

        System.out.println("Producer started.\n");

        //  Create scanner
        Scanner scanner = new Scanner(System.in);

        //  Create test Integer[] Object
        Integer[] arrayToSort;

        System.out.println("Enter the amount of integers to generate: ");
        int amountOfIntegers = scanner.nextInt();

        System.out.println("Generating " + amountOfIntegers + " integers...\n");

        //  Fill test Array Object
        arrayToSort = GenerateArray(amountOfIntegers);

        //  Create connection with ActiveMQ
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //  Destination Queue for Producer
        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);

        TextMessage message = session.createTextMessage(Arrays.toString(arrayToSort));
        producer.send(message);

        System.out.println("Message sent to testQueue1.\n");
        System.out.println("Start BucketSortfromQueue.main() to continue..");

        connection.close();
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

    /**
     * Check wether the input array is sorted or not
     * @param listToBeChecked
     * @return true if array is sorted
     */
    public static boolean isSorted(int[] listToBeChecked) {
        for (int i = 0; i < listToBeChecked.length - 1; i++) {
            if (listToBeChecked[i] > listToBeChecked[i + 1])
                return false;
        }
        return true;
    }
}
