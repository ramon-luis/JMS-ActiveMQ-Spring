package activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.awt.*;
import java.net.URI;

/**
 * MessageReceiver is associated with a specific queue.  Once created, it listens for a message at the associated
 * queue and then processes the message once received.
 */

class MessageReceiver implements ExceptionListener {

    MessageReceiver(String sQueueName) {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(sQueueName);

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer.receive(1000);

            // process if receive a message that is a TextMessage
            if (message instanceof TextMessage) {
                // extract the text
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();

                // process the order -> dummy method for example
                processOrder();

                // print to console
                System.out.println();
                System.out.println("Received: " + text);
                System.out.println();
                System.out.println("Now performing tasks that happen when a user purchases item...");
                System.out.println();
            }

            // clean things up
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occurred.  Shutting down client.");
    }

    private void processOrder() {
        String sURL = "http://i.ebayimg.com/00/s/NTAwWDUwMA==/z/CpoAAOSwhcJWQvwb/$_58.JPG";

        if(Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(sURL));
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }
}