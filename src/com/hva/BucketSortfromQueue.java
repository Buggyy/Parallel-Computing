package com.hva;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;

/**
 * The ActiveMQ using the BucketSort algorithm
 */
public class BucketSortfromQueue {
    static final String ACTIVEMQ_URL = "failover:(tcp://127.0.0.1:61616,localhost:8161)";

    private static String subjectFrom = "testQueue1";
    private static String subjectTo = "testQueue2";

    public static void main(String args[]) throws Exception {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination_fromQueue = session.createQueue(subjectFrom);
        MessageConsumer consumer = session.createConsumer(destination_fromQueue);
        Message message = consumer.receive();


        Integer[] arrayToSort = null;

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            //  Get String array back
            String strArray = textMessage.getText();
            System.out.println("before format " + strArray);

            //  Store string integers retrieved from queue
            String[] integers = strArray
                    .replaceAll("\\[", "")
                    .replaceAll("\\]", "")
                    .replaceAll("\\s", "")
                    .split(",");

            System.out.println("integers format " + strArray);


            arrayToSort = new Integer[integers.length];

            for (int i = 0; i < arrayToSort.length; i++) {
                try {
                    arrayToSort[i] = Integer.parseInt(integers[i]);
                } catch (NumberFormatException nfe) {
                    // Code, to recover from formatting errors
                }

            }
        }

        Destination destination_toQueue = session.createQueue(subjectTo);
        MessageProducer producer = session.createProducer(destination_toQueue);

        //  Default Bucket Size = 5
        BucketSort.sort(arrayToSort);

        String stringForConsumer = Arrays.toString(arrayToSort);

        TextMessage messageTo = session.createTextMessage(stringForConsumer);
        producer.send(messageTo);
        connection.close();
    }
}

