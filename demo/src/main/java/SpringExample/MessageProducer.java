//package SpringExample;
//
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//
///**
// * Created by RAM0N on 8/12/16.
// */
//class MessageProducer {
//
//    private JmsTemplate mJmsTemplate;
//
//    // constructor
//    MessageProducer(ConfigurableApplicationContext context) {
//        mJmsTemplate = context.getBean(JmsTemplate.class);
//    }
//
//    public void sendMessage(String sQueueName, String sMessage) {
//        // create the message
//        MessageCreator messageCreator = session -> session.createTextMessage(sMessage);
//        // print to console
//        System.out.println();
//        System.out.println("Sending message: " + sMessage);
//
//        // send the message
//        mJmsTemplate.send(sQueueName, messageCreator);
//    }
//
//}
