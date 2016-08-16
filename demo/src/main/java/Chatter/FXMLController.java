package Chatter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is an example of using Spring and JMS for an instant messaging application.  The application displays two
 * windows that represent different users.  Text messages from the application are pased to a MessageSender with a
 * specific destination queue that represents a user.  In this example, the user queues are hardcoded, but in a
 * real-world application the queue would be updated based on the users present in the chatroom.  Each active user
 * in the chatroom has a unique listener based on their queue.  When messages are received at a users queue, the
 * MessageListener parses the sender and message, and then sends the data back to the Controller.
 *
 * Message sends and receipts are printed to the console: note that message sending and listening occur on different
 * threads without any direct implementation of passing data between threads: the JMS message broker handles the
 * transfer of data between threads.  (All updates the UI are called from the main FX thread.)
 */

class FXMLController implements Initializable {

    // FXML elements
    @FXML private HBox mHBox;
    @FXML private VBox VbKanye;
    @FXML private ListView lstKanye;
    @FXML private TextArea txtAreaKanye;
    @FXML private Button btnKanye;
    @FXML private VBox vbKim;
    @FXML private ListView lstKim;
    @FXML private TextArea txtAreaKim;
    @FXML private Button btnKim;

    // constants
    private static final String QUEUE_KANYE = "KANYE";
    private static final String QUEUE_KIM = "KIM";

    // members
    private static FXMLController instance = null;
    private ConfigurableApplicationContext mContext;
    private MessageSender msgSender;
    private ObservableList<String> mMessagesKanye;
    private ObservableList<String> mMessagesKim;

    // constructor
    private FXMLController() {}

    // use to access controller MessageReceiver class
    static FXMLController getInstance() {
        if (instance == null) {
            instance = new FXMLController();
        }
        return instance;
    }

    // pass the Spring application context to the controller
    void setContext(ConfigurableApplicationContext context) {
        mContext = context;
    }

    // initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // define list to store messages
        mMessagesKanye = FXCollections.observableArrayList();
        mMessagesKim = FXCollections.observableArrayList();

        // create a message listener
        msgSender = new MessageSender(mContext);


        // send messages on enter
        txtAreaKanye.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                sendMessage(QUEUE_KIM, txtAreaKanye.getText(), QUEUE_KANYE);
            }
        });
        txtAreaKim.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                sendMessage(QUEUE_KANYE, txtAreaKim.getText(), QUEUE_KIM);
            }
        });

        // send messages on button
        btnKanye.setOnAction(e -> sendMessage(QUEUE_KIM, txtAreaKanye.getText(), QUEUE_KANYE));
        btnKim.setOnAction(e -> sendMessage(QUEUE_KANYE, txtAreaKim.getText(), QUEUE_KIM));
    } // end initialize

    //********** APPLICATION METHODS ****************//

    // send a message
    private void sendMessage(String sDestinationQueue, String sMessage, String sSender) {
        String sText = sSender.toLowerCase() + ": " + sMessage.trim();  // get the text
        msgSender.sendMessage(sDestinationQueue, sText);  // send the message
        postMessageOwnWindow(sDestinationQueue, sMessage);  // put a copy in the current sDestinationQueue window
        clearTextEntry(sDestinationQueue);  // clear text
        updateMessageWindows();  // update the display
    }

    // clear text from the text entry screen
    private void clearTextEntry(String sDestinationQueue) {
        if (sDestinationQueue.equals(QUEUE_KIM)) {
            txtAreaKanye.clear();
            txtAreaKanye.positionCaret(0);
        } else {
            txtAreaKim.clear();
            txtAreaKim.positionCaret(0);
        }
    }

    // post a message to own window: add to users list of messages
    private void postMessageOwnWindow(String sDestinationQueue, String sMessage) {
        String sLabel = "me: ";
        if (sDestinationQueue.equals(QUEUE_KIM)) {
            mMessagesKanye.add(sLabel + sMessage);
        } else {
            mMessagesKim.add(sLabel + sMessage);
        }
    }

    // update the message windows based on current list of messages
    private void updateMessageWindows() {
        lstKanye.setItems(mMessagesKanye);
        lstKim.setItems(mMessagesKim);
    }


    //********** ONLY CALLED FROM REMOTE CLASS ****************//

    // process message: called from message consumer
    void getMessageFromMessageBroker(String sRecipient, String sMessage) {
        // lambda for a new runnable: only update the GUI from main javafx thread
        Platform.runLater(() -> {
            if (sRecipient.equals(QUEUE_KIM)) {
                mMessagesKim.add(sMessage);
            } else {
                mMessagesKanye.add(sMessage);
            }
            updateMessageWindows();
        });
    }


}