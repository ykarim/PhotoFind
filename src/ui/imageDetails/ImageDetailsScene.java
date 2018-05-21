package ui.imageDetails;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import media.Picture;
import ui.util.Bundle;

import java.io.IOException;

public class ImageDetailsScene {

    private Bundle<Picture> pictureBundle;

    public ImageDetailsScene(Bundle<Picture> pictureBundle) {
        this.pictureBundle = pictureBundle;
    }

    public void start(Stage stage) {
        FXMLLoader imageDetailsLoader = new FXMLLoader();
        imageDetailsLoader.setLocation(getClass().getResource("../../res/fxml/imageDetails/imageDetails.fxml"));

        Parent root = null;

        try {
            root = imageDetailsLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            ImageDetailsController imageDetailsController = imageDetailsLoader.getController();
            imageDetailsController.initialize(pictureBundle);

            Scene scene = new Scene(root, 600, 480);
            stage.setScene(scene);
            stage.show();
        }
    }
}
