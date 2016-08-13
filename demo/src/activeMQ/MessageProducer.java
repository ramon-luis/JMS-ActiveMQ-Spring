import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * Created by RAM0N on 8/12/16.
 */
public class MessageProducer implements Runnable {

    private static final String QUEUE_NAME = "DON'T MIND";

    // variable to store message
    String mMessage;

    // constructor
    public MessageProducer(String sMessage) {
        mMessage = sMessage;
    }


    public void run() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(QUEUE_NAME);

            // Create a MessageProducer from the Session to the Topic or Queue
            javax.jms.MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a message
            String text = mMessage; //+ " From: " + Thread.currentThread().getName();
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            producer.send(message);
            System.out.println();
            System.out.println(Thread.currentThread().getName() + " Sent message: " + text);

            // Clean up
            session.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
