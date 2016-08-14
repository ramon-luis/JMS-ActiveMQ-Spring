package activeMQThreaded;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * MessageReceiver is associated with a specific queue.  Once created, it listens for a message at the associated
 * queue and then processes the message once received.  This implementation is created to receive a single message,
 * after which this specific MessageReceiver object is shutdown.
 */

class MessageReceiver implements Runnable, ExceptionListener {

    private String mQueueName;

    // constructor
    MessageReceiver(String sQueueName) {
        this.mQueueName = sQueueName;
    }

    // implements run in order to pass to individual thread
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
            Destination destination = session.createQueue(mQueueName);

            // Create a MessageConsumer from the Session to the Topic or Queue
            javax.jms.MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println();
                System.out.println(Thread.currentThread().getName() + " Received: " + text);

                // process the text
                switch (text) {
                    case "Pardon my French":
                        System.out.println(Thread.currentThread().getName() + " Reply: Bonjour Madame");
                        break;
                    case "Sak pase":
                        System.out.println(Thread.currentThread().getName() + " Reply: N'ap boule");
                        break;
                    default:
                        System.out.println(Thread.currentThread().getName() + " Reply: ...");
                        break;
                }
                System.out.println();
            }

            // clean up
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    // error handling
    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }
}

