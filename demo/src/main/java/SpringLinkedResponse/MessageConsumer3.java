package SpringLinkedResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;

/**
 * Created by RAM0N on 8/12/16.
 */

@Component
public class MessageConsumer3 {

    private static final String INCOMING_QUEUE = "LINE 3";

    // get a copy of the current context
    @Autowired
    ConfigurableApplicationContext context;

    // do something with the message
    @JmsListener(destination = INCOMING_QUEUE)
    public void receiveMessage(String message) {
        String sLine3 = "who did this to you?";
        System.out.println("Received: (Q:" + INCOMING_QUEUE + ")\n" + message);
        System.out.println();
        System.out.println("Poem complete: \n" + message + "\n" + sLine3);
        System.out.println();

    }
}
