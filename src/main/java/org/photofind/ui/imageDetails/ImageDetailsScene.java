package org.photofind.ui.imageDetails;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.photofind.media.Picture;
import org.photofind.ui.util.AppController;
import org.photofind.ui.util.AppScene;
import org.photofind.ui.util.Bundle;
import org.photofind.util.FileImport;

import java.io.IOException;

public class ImageDetailsScene implements AppScene {

    private Bundle<Picture> pictureBundle;
    private Parent root;
    private ImageDetailsController imageDetailsController;
    private boolean isStarted;

    public ImageDetailsScene(Bundle<Picture> pictureBundle) {
        this.pictureBundle = pictureBundle;
    }

    @Override
    public Parent start() {
        FXMLLoader imageDetailsLoader = new FXMLLoader();
        imageDetailsLoader.setLocation(FileImport.getFileURL(FileImport.importLocalFile("fxml/imageDetails/imageDetails.fxml")));

        try {
            root = imageDetailsLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            imageDetailsController = imageDetailsLoader.getController();
            imageDetailsController.initialize(pictureBundle);
        }

        return root;
    }

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public AppController getController() {
        return imageDetailsController;
    }

    @Override
    public Type getType() {
        return Type.IMAGEDETAILS;
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
