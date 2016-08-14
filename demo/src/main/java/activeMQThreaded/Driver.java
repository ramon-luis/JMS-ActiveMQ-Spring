package activeMQThreaded;

/**
 * This is a multi-threaded example of using activeMQ for point-to-point communication.  Multiple MessageSenders and
 * MessageReceivers are created on unique threads.  Messages are sent and received among the threads with no
 * direct implementation to handle concurrency.  The messages are exchanged among the threads by the ActiveMQ
 * MessageBroker that contains the shared queue.  Note that only the name of the queue is defined as a string: there
 * is no common queue object passed among threads.
 *
 * Five messages are sent.  The last MessageSender and the last MessageReceiver are on different queues.  Therefore,
 * the last message sent will not be received.
 *
 * The console may not print messages in order sent/received since thread processing order is a delegated task.
 *
 * Be sure to update the POM file to use ActiveMQ only before running this program.
 */
public class Driver {

    public static void main(String[] args) throws Exception {

        // print descriptive text
        printAppDescription();

        // queue name
        String sDontMindQueue = "DON'T MIND QUEUE";
        String sDoMindQueue = "DO MIND QUEUE";
        String sDifferentQueue = "NOT INTERESTED QUEUE";

        // messages to send
        String sMessage1 = "Hola, Como estas?";
        String sMessage2 = "Konnichiwa";
        String sMessage3 = "Pardon my French";
        String sMessage4 = "Sak pase";
        String sMessage5 = "I said, Hola, Como estas?";

        // send messages on different threads
        startNewThread(new MessageSender(sDontMindQueue, sMessage1), false);
        startNewThread(new MessageSender(sDontMindQueue, sMessage2), false);
        startNewThread(new MessageSender(sDontMindQueue, sMessage3), false);
        startNewThread(new MessageSender(sDontMindQueue, sMessage4), false);
        startNewThread(new MessageSender(sDoMindQueue, sMessage5), false);  // sent to different queue

        // receive messages on different threads
        startNewThread(new MessageReceiver(sDontMindQueue), false);
        startNewThread(new MessageReceiver(sDontMindQueue), false);
        startNewThread(new MessageReceiver(sDontMindQueue), false);
        startNewThread(new MessageReceiver(sDontMindQueue), false);
        startNewThread(new MessageReceiver(sDifferentQueue), false);  // listening on different queue
    }


    // helper method to start a new thread
    private static void startNewThread(Runnable runnable, boolean daemon) {
        Thread independentThread = new Thread(runnable);
        independentThread.setDaemon(daemon);
        independentThread.start();
    }

    // helper method to print app description
    private static void printAppDescription() {
        System.out.println();
        System.out.println("**************************************");
        System.out.println("Top Billboard Musician Kent Jones teaches foreign languages");
        System.out.println("**************************************");
        System.out.println();
    }

}