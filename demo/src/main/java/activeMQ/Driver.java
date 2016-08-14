package activeMQ;

/**
 * This is a simple example of using activeMQ for point-to-point communication.  A single message is sent to a queue
 * by a MessageSender and picked up from the queue by a MessageReceiver.
 *
 * Be sure to update the POM file to use ActiveMQ only before running this program.
 */

public class Driver {

    public static void main(String[] args) throws Exception {
        // print descriptive text
        printAppDescription();

        // name the queue for the sender and receiver
        String sQueueName = "orderQueue";

        // create a message producer that is associated with a specific queue
        MessageSender messageSender = new MessageSender(sQueueName);

        // define message to send
        String sMessage = "customer #41432 purchased a Nicolas Cage Pillowcase";

        // send the message
        messageSender.sendMessage(sMessage);

        // create a message consumer that is associated with a specific queue
        // it will automatically listen for messages that are in the queue
        new MessageReceiver(sQueueName);

        // shut everything down
        System.exit(0);
    }

    // helper method to print app description
    private static void printAppDescription() {
        System.out.println();
        System.out.println("**************************************");
        System.out.println("Send message to order processing queue");
        System.out.println("**************************************");
        System.out.println();
    }

}