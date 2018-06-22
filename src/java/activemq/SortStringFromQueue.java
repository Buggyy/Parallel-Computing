package java.activemq;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.algoritmes.ConcurrentMergeSort;
import static java.helper.Config.ACTIVEMQ_URL;
import static java.helper.Config.FROM_CSP;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class SortStringFromQueue {

    private static String subjectFrom = "test1";
    private static String subjectTo = "test2";

    public static void main(String args[]) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination_fromQueue;

        if (args.length > 0) {
            destination_fromQueue = session.createQueue(args[0]);
        } else {
            destination_fromQueue = session.createQueue(FROM_CSP);
        }

        MessageConsumer messageConsumer = session.createConsumer(destination_fromQueue);
        Message message = messageConsumer.receive();
        // bijhouden geconverte en gesorteerde nummers
        int[] integerList = null;
        if (message instanceof TextMessage) {

            TextMessage message = (TextMessage) message;
            String string = message.getText();
            // bijhouden string van nummers ontvangen van de queue
            String[] listOfIntegerStrings = string.split(" ");

            integerList = new int[listOfIntegerStrings.length];
            for (int i = 0; i < integerList.length; i++) {
                integerList[i] = Integer.parseInt(listOfIntegerStrings[i]);
            }
        }

        Destination destination = session.createQueue("resultaat");
        MessageProducer messageProducer = session.createProducer(destination);

        ConcurrentMergeSort concurrentMergeSort = new ConcurrentMergeSort(integerList);
        concurrentMergeSort.sort();

        String consumerString = utilities.CustomUtilities.arrayToString(integerList != null ? integerList : new int[0]);

        TextMessage messageTo = session.createTextMessage(consumerString);

        messageProducer.send(messageTo);

        //connection.close();
    }
}
