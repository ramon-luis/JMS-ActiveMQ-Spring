package SpringLinkedResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by RAM0N on 8/12/16.
 */

@Component
public class MessageConsumer2 {

    private static final String INCOMING_QUEUE = "LINE 2";
    private static final String OUTGOING_QUEUE = "LINE 3";

    // get a copy of the current context
    @Autowired
    ConfigurableApplicationContext context;

    // do something with the message
    @JmsListener(destination = INCOMING_QUEUE)
    @SendTo (OUTGOING_QUEUE)
    public String receiveMessage(String message) {
        String sLine3 = "I hold it close and whisper: ";
        System.out.println();
        System.out.println("Received: (Q:" + INCOMING_QUEUE + ")\n" + message);
        System.out.println("Sent: (Q:" + OUTGOING_QUEUE + ")\n" + message  + "\n" + sLine3);
        System.out.println();

        return message  + "\n" + sLine3;

    }
}
