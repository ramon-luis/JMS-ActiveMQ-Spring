package publishSubscribe;

import java.io.File;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.FileSystemUtils;

import javax.jms.ConnectionFactory;


@SpringBootApplication
@EnableJms
@EnableScheduling
public class Driver {

    private static final String TOPIC_NAME = "DRAKE'S BEST PICKUP LINES";

    public static void main(String[] args) {

        // string to send: Drake's best pickup lines
        String sMessage1 = "I need one dance";
        //String sMessage2 = "You used to call me on my cell phone";


        // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(Driver.class, args);

        // Send a message
        MessageProducer msgProducer = new MessageProducer(context);
        msgProducer.sendMessage(TOPIC_NAME, sMessage1);
        //msgProducer.sendMessage(TOPIC_NAME, sMessage2);



//         close the application and flush files
//        context.close();
//        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }


}
