package IM;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


/**
 * Created by RAM0N on 8/12/16.
 */
class MessageProducer {

    public enum USER {BOB, SALLY}
    private final String QUEUE = "msgQueue";
    private JmsTemplate mJmsTemplate;

    // constructor
    MessageProducer(ConfigurableApplicationContext context) {
        mJmsTemplate = context.getBean(JmsTemplate.class);
    }

    public void sendMessage(String sMessage, USER recipient) {
        // create the message
        MessageCreator messageCreator = session -> session.createTextMessage(recipient.toString() + ":" + sMessage);

        // print to console
        System.out.println();
        System.out.println("Sending message: " + sMessage + " (thread: " + Thread.currentThread().getName() + ")");

        // send the message
        mJmsTemplate.send(QUEUE, messageCreator);
    }

}
