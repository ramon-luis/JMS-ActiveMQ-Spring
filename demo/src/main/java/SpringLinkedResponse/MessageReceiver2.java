package SpringLinkedResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * MessageReceiver is associated with specific queues for incoming and outgoing.
 * Spring annotations do the heavy lifting of connecting & listening.
 * Note the "@SendTo" annotation and the return type for receiveMessages: this is what auto-sends a reply.
 */

@Component
public class MessageReceiver2 {

    // define the queues
    private static final String INCOMING_QUEUE = "LINE 2";
    private static final String OUTGOING_QUEUE = "LINE 3";

    @Autowired
    ConfigurableApplicationContext context;  // get a copy of the current context

    @JmsListener(destination = INCOMING_QUEUE)  // listen for message
    @SendTo (OUTGOING_QUEUE)  // send out when received
    public String receiveMessage(String message) {
        // do message actions
        String sLine3 = "I hold it close and whisper: ";
        System.out.println();
        System.out.println("Received: (Q:" + INCOMING_QUEUE + ")\n" + message);
        System.out.println("Sent: (Q:" + OUTGOING_QUEUE + ")\n" + message  + "\n" + sLine3);
        System.out.println();

        // this is what is being sent out
        return message  + "\n" + sLine3;

    }
}
