package SpringLinkedResponse;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.FileSystemUtils;


/**
 * Created by RAM0N on 8/12/16.
 */


@SpringBootApplication
@EnableJms
public class Driver {

    private static final String QUEUE_NAME = "LINE 1";

    public static void main(String[] args) {

        // string to send
        String sMessage1 = "When I see";

        // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(Driver.class, args);

        System.out.println();
        System.out.println("**************************************");
        System.out.println("Will Ferrell shares a sentimental poem");
        System.out.println("**************************************");
        System.out.println();

        // Send a message
        MessageProducer msgProducer = new MessageProducer(context);
        msgProducer.sendMessage(QUEUE_NAME, sMessage1);

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


}
