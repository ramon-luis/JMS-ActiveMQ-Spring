package publishSubscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by RAM0N on 8/12/16.
 */

@Component
public class MessageConsumer {

    private static final String TOPIC_NAME = "DRAKE'S BEST PICKUP LINES";

    // get a copy of the current context
    @Autowired
    ConfigurableApplicationContext context;

    // do something with the message
//    @JmsListener(containerFactory ="myJmsListenerContainerFactory", destination = TOPIC_NAME)
    @JmsListener(destination = TOPIC_NAME)
    public void receiveMessage(String message) {
//        String sReply = "...";
//        if (message.equals("I need one dance")) {
//            sReply = "I like your style";
//        } else if (message.equals("You used to call me on my cell phone")) {
//            sReply = "... (no answer: Drake did you wrong)";
//        }

        System.out.println("Received at consumer1: " + message);
//        System.out.println("Reply: " + sReply);
        System.out.println();

    }


}

