package activemq;
import algoritmes.ConcurrentMergeSort;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static helper.Config.ACTIVEMQ_URL;
import static helper.Config.FROM_CSP;
import static helper.CustomUtilities.arrayToString;

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
        // Bijhouden geconverte en gesorteerde nummers
        int[] integerList = null;
        if (message instanceof TextMessage) {

            TextMessage txtMessage = (TextMessage) message;
            String string = txtMessage.getText();

            // Bijhouden string van nummers ontvangen van de queue
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

        String consumerString = arrayToString(integerList != null ? integerList : new int[0]);

        TextMessage messageTo = session.createTextMessage(consumerString);

        messageProducer.send(messageTo);

        //connection.close();
    }
}
