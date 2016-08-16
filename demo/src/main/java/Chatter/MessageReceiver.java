package Chatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * MessageReceiver is associated with a specific queue.
 * Spring annotations do the heavy lifting of connecting & listening.
 *
 * Each user is associated with a specific queue
 */

@Component
public class MessageReceiver {

    // constants
    private static final String QUEUE_KANYE = "KANYE";
    private static final String QUEUE_KIM = "KIM";

    @Autowired
    ConfigurableApplicationContext context;  // get a copy of the current context

    // listener for Kanye
    @JmsListener(destination = QUEUE_KANYE)
    public void receiveMessageForKanye(String message) {
        processMessage(QUEUE_KANYE, message);

    }

    // listener for Kim
    @JmsListener(destination = QUEUE_KIM)
    public void receiveBobMessageForBob(String message) {
        processMessage(QUEUE_KIM, message);

    }

    // process the message
    private void processMessage(String sQueueName, String message) {
        // send data to the FXMLController
        FXMLController.getInstance().getMessageFromMessageBroker(sQueueName, message);

        // print to console
        System.out.println(sQueueName + " received: \"" + message + "\" on thread: " + Thread.currentThread().getName());
        System.out.println();

    }



}
