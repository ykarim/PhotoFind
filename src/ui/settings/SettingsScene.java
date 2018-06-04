package ui.settings;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ui.util.AppController;
import ui.util.AppScene;

import java.io.IOException;

public class SettingsScene implements AppScene {

    private Parent root = null;
    private SettingsController settingsController;
    private boolean isStarted;

    public SettingsScene() {

    }

    @Override
    public Parent start() {
        FXMLLoader settingsSceneLoader = new FXMLLoader();
        settingsSceneLoader.setLocation(getClass().getResource("../../res/fxml/settings/settings.fxml"));

        try {
            root = settingsSceneLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        settingsController = settingsSceneLoader.getController();
        settingsController.initialize(null);

        return root;
    }

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public AppController getController() {
        return settingsController;
    }

    @Override
    public Type getType() {
        return Type.SETTINGS;
    }

    @Override
    public boolean getStarted() {
        return isStarted;
    }

    @Override
    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }
}
