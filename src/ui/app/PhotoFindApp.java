package ui.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.dashboard.HomeDashboardController;

import java.io.IOException;

public class PhotoFindApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../res/fxml/dashboard/home.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HomeDashboardController homeDashboardController = loader.getController();
        homeDashboardController.initialize();

        Scene scene = new Scene(root, 600, 480);

        stage.setTitle("PhotoFind");
        stage.setScene(scene);
        stage.show();
    }
}
