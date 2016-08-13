package IM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


/**
 * Created by RAM0N on 8/12/16.
 */

@Component
public class MessageConsumer {

    private final String QUEUE = "msgQueue";

    // get a copy of the current context
    @Autowired
    ConfigurableApplicationContext context;

    @JmsListener(destination = QUEUE)
    public void receiveMessage(String message) {

        String receiver = message.substring(0, message.indexOf(":"));
        String text = message.substring(message.indexOf(":") + 1);
        FXMLController.getInstance().processNewMessage(text, receiver);

        System.out.println("Received: " + message);
        System.out.println();

    }

}
