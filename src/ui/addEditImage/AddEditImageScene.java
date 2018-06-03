package ui.addEditImage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.util.Bundle;

import java.io.IOException;

public class AddEditImageScene {

    private Mode mode;
    private Bundle bundle;

    public AddEditImageScene(Mode mode, Bundle bundle) {
        this.mode = mode;
        this.bundle = bundle;
    }

    public void start(Scene previousScene, Stage stage) {
        FXMLLoader addImagesLoader = new FXMLLoader();
        addImagesLoader.setLocation(getClass().getResource("../../res/fxml/addEditImage/addEditImage.fxml"));

        Parent root = null;

        try {
            root = addImagesLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            AddEditImageController addEditImageController = addImagesLoader.getController();

            addEditImageController.initializeScene(mode, previousScene, bundle);

            Scene scene = new Scene(root, 600, 480);
            stage.setScene(scene);
            stage.show();
        }
    }

    public enum Mode {
        ADD, EDIT
    }
}
