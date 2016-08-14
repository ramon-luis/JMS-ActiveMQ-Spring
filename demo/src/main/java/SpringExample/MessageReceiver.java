package SpringExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * MessageReceiver is associated with a specific queue.
 * Spring annotations do the heavy lifting of connecting & listening.
 */

@Component
public class MessageReceiver {

    @Autowired
    ConfigurableApplicationContext context;  // get a copy of the current context

    // define the queue
    private static final String QUEUE_NAME = "DRAKE'S BEST PICKUP LINES";

    @JmsListener(destination = QUEUE_NAME)  // listen for the message
    public void receiveMessage(String message) {
        // do message actions
        String sReply = "...";
        if (message.equals("I need one dance")) {
            sReply = "I like your style";
        } else if (message.equals("You used to call me on my cell phone")) {
            sReply = "... (no answer: Drake did you wrong)";
        }

        // print to console
        System.out.println("Received: " + message);
        System.out.println("Reply: " + sReply);
        System.out.println();

    }
}
