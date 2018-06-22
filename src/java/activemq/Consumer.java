package java.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.algoritmes.ConcurrentMergeSort;
import java.helper.CustomUtilities;
import java.util.ArrayList;
import java.util.List;

import static java.helper.Config.ACTIVEMQ_URL;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class Consumer {
    private static String subject = "resultaat";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageConsumer consumer = session.createConsumer(destination);

        int count = 0;

        List<Integer> list = new ArrayList<>();

        while (true) {

            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                count++;
                TextMessage textMessage = (TextMessage) message;

                // sorteren van de string van nummers ontvangen van de queue
                String str = textMessage.getText();
                String[] integerStrings = str.split(" ");

                for (String integerString : integerStrings) {
                    int j = Integer.parseInt(integerString);
                    list.add(j);
                }

                if (count == Producer.NODES) {
                    int[] array = list.stream().mapToInt(i -> i).toArray();
                    ConcurrentMergeSort concurrentMergeSort = new ConcurrentMergeSort(array);
                    concurrentMergeSort.sort();

                    if (CustomUtilities.isArraySorted(array))
                        break;
                }
            }
        }

        connection.close();
    }
}