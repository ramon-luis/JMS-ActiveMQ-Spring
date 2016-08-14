package SpringLinkedResponse;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.FileSystemUtils;


/**
 * This is an example of using Spring Boot annotations to automatically send new messages from a message listener
 * when a new message is received.  The effect is similar to a chain-reaction of messages that are independently
 * sent to different queues once an initial message is sent.
 *
 * The example uses Spring annotations to implement JMS and activeMQ for point-to-point communication.  A single
 * MessageSender is created: the MessageSender has a sendMessage() method that takes a
 * queue and message, and then will send the message to that specific queue.  Multipe MessageReceivers are
 * defined in order to listen on different queues.  There is no need to instantiate a MessageReceiver class: Spring
 * "wiring" annotations will have the receiver listen on the specific queue for messages.
 *
 * Be sure to update the POM file to use Spring & Spring-specific ActiveQM dependencies only before running this program.
 */


@SpringBootApplication
@EnableJms
public class Driver {

    private static final String QUEUE_1 = "LINE 1";

    public static void main(String[] args) {

        // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(Driver.class, args);

        // print descriptive text
        printAppDescription();

        // create a message sender
        MessageSender messageSender = new MessageSender(context);

        // string to send
        String sMessage1 = "When I see";

        // send the message
        messageSender.sendMessage(QUEUE_1, sMessage1);

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            System.out.println("error sleeping");
        }
        // close the application and flush files
        context.close();
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
        System.exit(0);

    }

    // helper method to print app description
    private static void printAppDescription() {
        System.out.println();
        System.out.println("**************************************");
        System.out.println("Will Ferrell shares a sentimental poem");
        System.out.println("**************************************");
        System.out.println();
    }


}
