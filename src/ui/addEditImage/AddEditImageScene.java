package ui.addEditImage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ui.util.AppController;
import ui.util.AppScene;
import ui.util.Bundle;

import java.io.IOException;

public class AddEditImageScene implements AppScene {

    private Mode mode;
    private Bundle bundle;
    private Parent root;
    private AddEditImageController addEditImageController;
    private boolean isStarted;

    public AddEditImageScene(Mode mode, Bundle bundle) {
        this.mode = mode;
        this.bundle = bundle;
    }

    @Override
    public Parent start() {
        FXMLLoader addImagesLoader = new FXMLLoader();
        addImagesLoader.setLocation(getClass().getResource("../../res/fxml/addEditImage/addEditImage.fxml"));

        try {
            root = addImagesLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            addEditImageController = addImagesLoader.getController();
            addEditImageController.setMode(mode);
            addEditImageController.initialize(bundle);
        }

        return root;
    }

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public AppController getController() {
        return addEditImageController;
    }

    @Override
    public Type getType() {
        return Type.ADDEDIT;
    }

    @Override
    public boolean getStarted() {
        return isStarted;
    }

    @Override
    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public enum Mode {
        ADD, EDIT
    }
}
