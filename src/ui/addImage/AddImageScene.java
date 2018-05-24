package ui.addImage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.util.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AddImageScene {

    private Bundle<List<File>> filesBundle;

    public AddImageScene(Bundle<List<File>> filesBundle) {
        this.filesBundle = filesBundle;
    }

    public void start(Scene previousScene, Stage stage) {
        FXMLLoader addImagesLoader = new FXMLLoader();
        addImagesLoader.setLocation(getClass().getResource("../../res/fxml/addImage/addImage.fxml"));

        Parent root = null;

        try {
            root = addImagesLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            AddImageController addImageController = addImagesLoader.getController();
            addImageController.initialize(previousScene, filesBundle);

            Scene scene = new Scene(root, 600, 480);
            stage.setScene(scene);
            stage.show();
        }
    }
}
