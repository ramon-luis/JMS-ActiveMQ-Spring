package SpringLinkedResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by RAM0N on 8/12/16.
 */

@Component
public class MessageConsumer1 {

    private static final String INCOMING_QUEUE = "LINE 1";
    private static final String OUTGOING_QUEUE = "LINE 2";

    // get a copy of the current context
    @Autowired
    ConfigurableApplicationContext context;


    // do something with the message
    @JmsListener(destination = INCOMING_QUEUE)
    @SendTo (OUTGOING_QUEUE)
    public String receiveMessage(String message) {
        String sLine2 = "a bruised apple at the grocery store,";
        System.out.println();
        System.out.println("Received: (Q:" + INCOMING_QUEUE + ")\n" + message);
        System.out.println("Sent: (Q:" + OUTGOING_QUEUE + ")\n" + message  + "\n" + sLine2);
        System.out.println();


        return message  + "\n" + sLine2;

    }
}
