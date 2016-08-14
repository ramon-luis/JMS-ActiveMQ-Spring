package publishSubscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by RAM0N on 8/12/16.
 */
@Component
class MessageProducer {

    private JmsTemplate mJmsTemplate;

    // constructor
    @Autowired
    MessageProducer(ConfigurableApplicationContext context) {
        mJmsTemplate = context.getBean(JmsTemplate.class);
        mJmsTemplate.setPubSubDomain(true);
        System.out.println("pub-sub: " + mJmsTemplate.isPubSubDomain());
    }

//    @Scheduled(fixedRate = 1000)
    public void sendMessage(String sTopicName, String sMessage) {
        // create the message
        MessageCreator messageCreator = session -> session.createTextMessage(sMessage);
        // print to console
        System.out.println();
        System.out.println("Sending message: " + sMessage);

        // send the message
        mJmsTemplate.send(sTopicName, messageCreator);
    }

}

