package ui.settings;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsScene {

    public SettingsScene() {
    }

    public void start(Scene previousScene, Stage stage) {
        FXMLLoader settingsSceneLoader = new FXMLLoader();
        settingsSceneLoader.setLocation(getClass().getResource("../../res/fxml/settings/settings.fxml"));

        Parent root = null;

        try {
            root = settingsSceneLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            SettingsController settingsController = settingsSceneLoader.getController();
            settingsController.initialize(previousScene);

            Scene scene = new Scene(root, 600, 480);
            stage.setScene(scene);
            stage.show();
        }
    }
}
