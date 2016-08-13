

/**
 * Hello world!
 */
public class ActiveMQExample {

    public static void main(String[] args) throws Exception {

        String sMessage1 = "Hola, Como estas?";
        String sMessage2 = "Konnichiwa";
        String sMessage3 = "Pardon my French";
        String sMessage4 = "Sak pase";

        // send messages on different threads
        thread(new MessageProducer(sMessage1), false);
        thread(new MessageProducer(sMessage2), false);
        thread(new MessageProducer(sMessage3), false);
        thread(new MessageProducer(sMessage4), false);

        // receive messages on different threads
        thread(new MessageConsumer(), false);
        thread(new MessageConsumer(), false);
        thread(new MessageConsumer(), false);
        thread(new MessageConsumer(), false);
    }


    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

}