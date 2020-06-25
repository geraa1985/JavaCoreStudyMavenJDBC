package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/msg.fxml"));
        primaryStage.setTitle("Messenger");
        primaryStage.setScene(new Scene(root, 400, 300, Color.AQUA));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(300);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
