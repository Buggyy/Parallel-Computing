package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {
  // either connect to the remote ActiveMQ running on the PI, or on the localhost
  private static String url = "failover:(tcp://169.254.1.1:61616,localhost:8161)";
  private static String subject = "testQueue1"; // Queue Name

  public static void main(String[] args) throws JMSException {
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
    Connection connection = connectionFactory.createConnection();
    connection.start();
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Destination destination = session.createQueue(subject);
    MessageProducer producer = session.createProducer(destination);

    String str = "12 15 21 100 121 01 11 19 54";

    TextMessage message = session.createTextMessage(str);
    producer.send(message);
    System.out.println("Sent Message '" + message.getText() + "'");
    connection.close();
  }
}
