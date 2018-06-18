package activemq;

import javax.jms.*;
import algorithm.ConcurrentMergeSort;
import org.apache.activemq.ActiveMQConnectionFactory;
import util.Utils;


/**
 * Maintained and created by:
 * Chris Verra
 * S. R. Lobato
 */

public class Consumer {
    // url voor het verbinden met de Raspberry PI
    Private static String url = "tcp://192.252.1.1:61616"  // klopt dit?
    Private static String topic = "onderwerp";

    Public static void main(String[] args) throws  JMSException {
        // connectie regelen
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connection Factory.createConnection();
        connection.start();

        // sessie aanmaken
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // sessie queue met het onderwerp
        Destination topic = session.createQueue(topic);
        MessageConsumer messageConsumer = session.createConsumer(topic);

        int counter = 0;

        List<Integer> = integerList = new ArrayList<>();

        while(true) {

            Message message = consumer.receive();

            if (message instanceof TextMessage) {
               counter++

               TextMessage txtMsg = (TextMessage) message;

               String string = textMessage.getText();
               // Opslaan van de nummers uit de queue
               String[] intStringList = string.split(" ");

               for (String intString : intStringList ) {
                   int i  = Integer.parseInt(intString);
                   integerList.add(i);
               }

                if (counter == Producer.NODES) {
                   int[] array = list.stream().mapToInt(i -> i).toArray();

                   ConcurrentMergeSort concurrentMergeSort = new ConcurrentMergeSort(array);
                   concurrentMergeSort.sort();
                }
            }
        }

        connection.close()
    }

}