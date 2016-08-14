package activeMQThreaded;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * MessageSender is associated with a specific queue.  Once created, the object can send messages to the associated
 * queue.  This implementation is created to send a single message, after which this specific MessageSender object
 * is shutdown.
 */

class MessageSender implements Runnable {

    // variable to store message
    private String mMessage;
    private String mQueueName;

    // constructor
    MessageSender(String sQueueName, String sMessage) {
        mMessage = sMessage;
        mQueueName = sQueueName;
    }

    // implements run in order to pass to individual thread
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
            Destination destination = session.createQueue(mQueueName);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a message
            String text = mMessage;
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            producer.send(message);

            // print to console
            System.out.println();
            System.out.println(Thread.currentThread().getName() + " Sent message: " + text);

            // Clean up
            session.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
