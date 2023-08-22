package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        initializeApplication(primaryStage, loader);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initializeApplication(Stage primaryStage, FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        primaryStage.setTitle("Logistics System");
        primaryStage.setScene(new Scene(root, 200, 150));
        primaryStage.show();
    }

}