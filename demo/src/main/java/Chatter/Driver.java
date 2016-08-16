package Chatter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.FileSystemUtils;

import java.io.File;

/**
 * * This is an example of using Spring and JMS for an instant messaging application. The user interface is built
 * with JavaFX.  See FXMLController for more implementation details.
 *
 * Be sure to update the POM file to use Spring & Spring-specific ActiveQM dependencies only before running this program.
 */

@SpringBootApplication
@EnableJms
public class Driver extends Application{

        private static String[] args;  // used to pass param to the context under start()

        public static void main(String[] args) {
            Driver.args = args;
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {

            // set the application context and delete any existing message data
            ConfigurableApplicationContext context = SpringApplication.run(Chatter.Driver.class, args);
            FileSystemUtils.deleteRecursively(new File("activemq-data"));

            // load the FXML and controller and do JavaFX things
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
            FXMLController controller = FXMLController.getInstance();
            controller.setContext(context);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add("/styles/Styles.css");  // placeholder for style sheet
            stage.setTitle("Kim & Kanye's Chatter App");
            stage.setScene(scene);

            // print descriptive text
            printAppDescription();

            // close everything out when window is closed
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                FileSystemUtils.deleteRecursively(new File("activemq-data"));
                context.close();
                System.exit(0);
            });

            stage.show();
        }

    // helper method to print app description
    private static void printAppDescription() {
        System.out.println();
        System.out.println("**************************************");
        System.out.println("Messaging App via Spring JMS");
        System.out.println("Queue per User -> JMS broker directs messages");
        System.out.println("**************************************");
        System.out.println();
    }

}
