package SpringExample;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.FileSystemUtils;

/**
 * This is a simple example of using Spring Boot annotations to implement JMS and activeMQ for point-to-point
 * communication.  A single MessageSender is created: the MessageSender has a sendMessage() method that takes a
 * queue and message, and then will send the message to that specific queue.  A single MessageReceiver has been
 * defined to listen on a specific queue.  There is no need to instantiate a MessageReceiver class: Spring
 * "wiring" annotations will have the receiver listen on the specific queue for messages.  A separate class of a
 * listening object can be created in order to receive on different queues.
 *
 * Be sure to update the POM file to use Spring & Spring-specific ActiveQM dependencies only before running this program.
 */

@SpringBootApplication  // define as a Spring Application
@EnableJms  // enable JMS for Spring in this program
public class Driver {

    private static final String QUEUE_NAME = "DRAKE'S BEST PICKUP LINES";

    public static void main(String[] args) {

        // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(Driver.class, args);

        // print descriptive text
        printAppDescription();

        // create a message producer
        MessageSender messageSender = new MessageSender(context);

        // string to send: Drake's best pickup lines
        String sMessage1 = "I need one dance";
        String sMessage2 = "You used to call me on my cell phone";

        // send messages to specific queue
        messageSender.sendMessage(QUEUE_NAME, sMessage1);
        messageSender.sendMessage(QUEUE_NAME, sMessage2);

        // close the application and flush files
        context.close();
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }

    // helper method to print app description
    private static void printAppDescription() {
        System.out.println();
        System.out.println("**************************************");
        System.out.println("How people in Drake's songs reply to his best pickup lines");
        System.out.println("**************************************");
        System.out.println();
    }


}
