package ui.app;

import controller.PictureDAOImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import media.Picture;
import media.descriptors.Caption;
import media.descriptors.Description;
import ui.dashboard.HomeDashboardController;
import util.FileImport;

import java.io.IOException;
import java.util.ArrayList;

public class PhotoFindApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        for (int i = 0; i <= 60; i++) {
            Picture picture = new Picture(FileImport.importLocalFile("../res/img/f.png")); //if file path is wrong program crashes fix this
            picture.addTag("TOY", 1);

            ArrayList<Caption> captions = new ArrayList<>();

            for (int c = 0; c <= 3; c++) {
                captions.add(new Caption("Test", 1.0));
            }

            picture.addDescription(new Description(captions, null));
            new PictureDAOImpl().addPicture(picture);
        }

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
