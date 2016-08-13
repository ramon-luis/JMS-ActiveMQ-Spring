import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * Created by RAM0N on 8/12/16.
 */

public class MessageConsumer implements Runnable, ExceptionListener {

    private static final String QUEUE_NAME = "DON'T MIND";
//    private static final String QUEUE_NAME = "I DO MIND";

    public void run() {
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
            Destination destination = session.createQueue(QUEUE_NAME);

            // Create a MessageConsumer from the Session to the Topic or Queue
            javax.jms.MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println();
                System.out.println(Thread.currentThread().getName() + " Received: " + text);
                if (text.equals("Pardon my French")) {
                    System.out.println(Thread.currentThread().getName() + " Reply: Bonjour Madame");
                } else if (text.equals("Sak pase")) {
                    System.out.println(Thread.currentThread().getName() + " Reply: N'ap boule");

                } else {
                    System.out.println(Thread.currentThread().getName() + " Reply: ...");

                }
                System.out.println();
            } else {
                System.out.println(Thread.currentThread().getName() + " Received: " + message);
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }
}

