package SpringLinkedResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * MessageReceiver is associated with specific queues for incoming and outgoing.
 * Spring annotations do the heavy lifting of connecting & listening.
 * Note the "@SendTo" annotation and the return type for receiveMessages: this is what auto-sends a reply.
 */

@Component
public class MessageReceiver1 {

    // define the queues
    private static final String INCOMING_QUEUE = "LINE 1";
    private static final String OUTGOING_QUEUE = "LINE 2";

    @Autowired
    ConfigurableApplicationContext context;  // get a copy of the current context

    @JmsListener(destination = INCOMING_QUEUE)  // listen for message
    @SendTo (OUTGOING_QUEUE)  // send out when received
    public String receiveMessage(String message) {
        // do message actions
        String sLine2 = "a bruised apple at the grocery store,";
        System.out.println();
        System.out.println("Received: (Q:" + INCOMING_QUEUE + ")\n" + message);
        System.out.println("Sent: (Q:" + OUTGOING_QUEUE + ")\n" + message  + "\n" + sLine2);
        System.out.println();

        // this is what is being sent out
        return message  + "\n" + sLine2;

    }
}
