package SpringLinkedResponse;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Created by RAM0N on 8/13/16.
 */

class MessageProducer {

    private JmsTemplate mJmsTemplate;

    // constructor
    MessageProducer(ConfigurableApplicationContext context) {
        mJmsTemplate = context.getBean(JmsTemplate.class);
    }

    void sendMessage(String sQueueName, String sMessage) {
        // create the message
        MessageCreator messageCreator = session -> session.createTextMessage(sMessage);
        // print to console
        System.out.println();
        System.out.println("Sending message: (Q:" + sQueueName + ")\n" + sMessage);

        // send the message
        mJmsTemplate.send(sQueueName, messageCreator);
    }

}
