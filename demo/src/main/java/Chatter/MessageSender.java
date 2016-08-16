package Chatter;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


/**
 * MessageSender is NOT associated with a specific queue.  It's send message method can send messages to different
 * queues.  The JmsTemplate is a Spring helper class that does the work of creating connections, etc.
 *
 * Users of the application are identified by individual queues.
 */

class MessageSender {

    // this does the real work
    // http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/core/JmsTemplate.html
    private JmsTemplate mJmsTemplate;

    // constructor
    MessageSender(ConfigurableApplicationContext context) { mJmsTemplate = context.getBean(JmsTemplate.class); }


    // call to send messages to a queue
    void sendMessage(String sQueueName, String sMessage) {
        // create the message
        MessageCreator messageCreator = session -> session.createTextMessage(sMessage);

        // print to console
        System.out.println();
        System.out.println("Sending message: \"" + sMessage + "\" to: " + sQueueName + " on thread: " + Thread.currentThread().getName());

        // send the message
        mJmsTemplate.send(sQueueName, messageCreator);
    }

}
