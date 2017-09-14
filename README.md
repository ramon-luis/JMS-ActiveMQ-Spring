# README #

This repo provides information about the Java Messaging System (JMS) using ActiveMQ and Spring.

Materials include:

* presentation that covers JMS, ActiveMQ, and Spring
* simple lab projects that demonstrate how to use JMS, ActiveMQ, and Spring in Java8

<kbd>
	<img src="https://github.com/ramon-luis/JMS-ActiveMQ-Spring/raw/master/chatter-screenshot-1.png">
</kbd>
<br />
<br />


### Presentation Topics ###
* Messaging
* JMS
* ActiveMQ
* Additional Resources

### activeMQ project ###
A simple example of using activeMQ for point-to-point communication.  A single message is sent to a queue by a MessageSender and picked up from the queue by a MessageReceiver.

### activeMQThreaded project ###
A multi-threaded example of using activeMQ for point-to-point communication.  Multiple MessageSenders and MessageReceivers are created on unique threads.  Messages are sent and received among the threads with no direct implementation to handle concurrency.  The messages are exchanged among the threads by the ActiveMQ MessageBroker that contains the shared queue.  Note that only the name of the queue is defined as a string: there is no common queue object or immutable data structure passed among threads.

### SpringExample project ###
A simple example of using Spring Boot annotations to implement JMS and activeMQ for point-to-point communication.  A single MessageSender is created: the MessageSender has a sendMessage() method that takes a queue and message, and then will send the message to that specific queue.  A single MessageReceiver has been defined to listen on a specific queue.  There is no need to instantiate a MessageReceiver class: Spring "wiring" annotations will have the receiver listen on the specific queue for messages.  A separate class of a listening object can be created in order to receive on different queues.

### SpringLinkedResponse project ###
An example of using Spring Boot annotations to automatically send new messages from a message listener when a new message is received.  The effect is similar to a chain-reaction of messages that are independently sent to different queues once an initial message is sent. The example uses Spring annotations to implement JMS and activeMQ for point-to-point communication.  A single MessageSender is created: the MessageSender has a sendMessage() method that takes a queue and message, and then will send the message to that specific queue.  Multipe MessageReceivers are defined in order to listen on different queues.  There is no need to instantiate a MessageReceiver class: Spring "wiring" annotations will have the receiver listen on the specific queue for messages.

### Chatter project ###
An example of using Spring and JMS for an instant messaging application.  The application displays two windows that represent different users.  Text messages from the application are pased to a MessageSender with a specific destination queue that represents a user.  In this example, the user queues are hardcoded, but in a real-world application the queue would be updated based on the users present in the chatroom.  Each active user in the chatroom has a unique listener based on their queue.  When messages are received at a users queue, the MessageListener parses the sender and message, and then sends the data back to the Controller.  Message sends and receipts are printed to the console: note that message sending and listening occur on different threads without any direct implementation of passing data between threads: the JMS message broker handles the transfer of data between threads.  (All updates the UI are called from the main FX thread.)