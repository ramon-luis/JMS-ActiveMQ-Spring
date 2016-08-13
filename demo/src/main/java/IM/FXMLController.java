package IM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import IM.MessageProducer.USER;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by RAM0N on 8/13/16.
 */

@SpringBootApplication
@EnableJms
public class FXMLController implements Initializable {

    @FXML private HBox mHBox;
    @FXML private VBox vbLeft;
    @FXML private ListView lstLeft;
    @FXML private TextArea txtAreaLeft;
    @FXML private Button btnLeft;
    @FXML private VBox vbRight;
    @FXML private ListView lstRight;
    @FXML private TextArea txtAreaRight;
    @FXML private Button btnRight;

    private MessageProducer msgSender;
    private ConfigurableApplicationContext mContext;

    private ObservableList<String> mMessagesLeft;
    private ObservableList<String> mMessagesRight;

    private static FXMLController instance = null;

    FXMLController() {}

    public static FXMLController getInstance() {
        if (instance == null) {
            instance = new FXMLController();
        }
        return instance;
    }

    public void setContext( ConfigurableApplicationContext context) {
        mContext = context;
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mMessagesLeft = FXCollections.observableArrayList();
        mMessagesRight = FXCollections.observableArrayList();

        msgSender = new MessageProducer(mContext);

        btnLeft.setOnAction(e -> sendMessage(txtAreaLeft.getText(), USER.SALLY));
        btnRight.setOnAction(e -> sendMessage(txtAreaRight.getText(), USER.BOB));
    }

    private void sendMessage(String sMessage, USER user) {
        msgSender.sendMessage(sMessage, user, this);
        postMessageOwnWindow(sMessage, user);
        clearTextEntry(user);
        updateMessageWindows();
    }

    private void clearTextEntry(USER user) {
        if (user == USER.BOB) {
            txtAreaRight.clear();
        } else {
            txtAreaLeft.clear();
        }
    }

    private void postMessageOwnWindow(String sMessage, USER user) {
        if (user == USER.BOB) {
            mMessagesRight.add(sMessage);
        } else {
            mMessagesLeft.add(sMessage);
        }
    }

    private void updateMessageWindows() {
        lstLeft.setItems(mMessagesLeft);
        lstRight.setItems(mMessagesRight);
    }


    // called from message consumer
    void processNewMessage(String sMessage, String sRecipient) {
        if (sRecipient.equals(USER.BOB.toString())) {
            mMessagesRight.add(sMessage);
        } else {
            mMessagesLeft.add(sMessage);
        }
        updateMessageWindows();
    }


}