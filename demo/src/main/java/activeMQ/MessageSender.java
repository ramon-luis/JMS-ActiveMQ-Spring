package activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * MessageSender is associated with a specific queue.  Once created, the object can send messages to the associated
 * queue.
 */
class MessageSender {

    private Session mSession;
    private MessageProducer mProducer;

    // constructor
    MessageSender(String sQueueName) {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            mSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = mSession.createQueue(sQueueName);

            // Create a MessageProducer from the Session to the Topic or Queue
            mProducer = mSession.createProducer(destination);
            mProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    // send a message with this MessageSender object
    void sendMessage(String sMessage) {
        try {
            // create a TextMessage object
            TextMessage message = mSession.createTextMessage(sMessage);

            // Tell the producer to send the message
            mProducer.send(message);

            // print to console
            System.out.println();
            System.out.println("Sent message: " + sMessage);

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

}