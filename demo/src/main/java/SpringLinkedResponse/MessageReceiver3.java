package SpringLinkedResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;

/**
 * MessageReceiver is associated with a specific queue.
 * Spring annotations do the heavy lifting of connecting & listening.
 */

@Component
public class MessageReceiver3 {

    // define the queue: no outgoing, last stop
    private static final String INCOMING_QUEUE = "LINE 3";

    @Autowired
    ConfigurableApplicationContext context;  // get a copy of the current context

    @JmsListener(destination = INCOMING_QUEUE)  // listen for message
    public void receiveMessage(String message) {
        // do message actions
        String sLine3 = "who did this to you?";

        // print console
        System.out.println("Received: (Q:" + INCOMING_QUEUE + ")\n" + message);
        System.out.println();

        // final action
        System.out.println("Poem complete: \n" + message + "\n" + sLine3);
        System.out.println();
    }
}
