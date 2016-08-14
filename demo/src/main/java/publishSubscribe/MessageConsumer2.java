package publishSubscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by RAM0N on 8/12/16.
 */

@Component
public class MessageConsumer2 {

    private static final String TOPIC_NAME = "DRAKE'S BEST PICKUP LINES";

    // get a copy of the current context
    @Autowired
    ConfigurableApplicationContext context;

    // do something with the message
    @JmsListener(destination = TOPIC_NAME)
    public void receiveMessage(String message) {

        System.out.println("Received at consumer2: " + message);
        System.out.println();
    }
}