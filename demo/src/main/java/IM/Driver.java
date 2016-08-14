package IM;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.FileSystemUtils;

import java.io.File;

/**
 * Created by RAM0N on 8/13/16.
 */

@SpringBootApplication
@EnableJms
public class Driver extends Application{

        private static String[] args;

        public static void main(String[] args) {
            Driver.args = args;
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {

            ConfigurableApplicationContext context = SpringApplication.run(IM.Driver.class, args);
            FileSystemUtils.deleteRecursively(new File("activemq-data"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
            FXMLController controller = FXMLController.getInstance();
            controller.setContext(context);
            loader.setController(controller);
            Parent root = loader.load();

            Scene scene = new Scene(root);
//            scene.getStylesheets().add("/styles/Styles.css");

            stage.setTitle("Chat");
            stage.setScene(scene);

            System.out.println();
            System.out.println("**************************************");
            System.out.println("JMS Implementation for Messaging App");
            System.out.println("Queue per User -> JMS broker directs messages");
            System.out.println("**************************************");
            System.out.println();

            stage.setOnCloseRequest(event -> {
                Platform.exit();
                FileSystemUtils.deleteRecursively(new File("activemq-data"));
                context.close();
                System.exit(0);
            });
            stage.show();
        }

}
