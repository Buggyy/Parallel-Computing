package com.hva;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import java.util.Arrays;

import static com.hva.BucketSortfromQueue.ACTIVEMQ_URL;

/**
 * The ActiveMQ Producer Class
 *
 */
public class Producer {
    //    Queue name of Producer
    private static String subject = "testQueue1";

    public static void main(String[] args) throws JMSException {

        //  Create test Integer[] Object
        Integer[] arrayToSort;

        //  Fill test Array Object
        arrayToSort = GenerateArray(1000000);

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

        System.out.println("Sent Message '" + message.getText() + "'");

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
}
