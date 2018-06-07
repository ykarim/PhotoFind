package org.photofind.ui.dashboard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.photofind.ui.util.AppController;
import org.photofind.ui.util.AppScene;
import org.photofind.util.FileImport;

import java.io.IOException;

public class DashboardScene implements AppScene {

    private Parent root;
    private HomeDashboardController homeDashboardController;
    private boolean isStarted;

    @Override
    public Parent start() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FileImport.getFileURL(FileImport.importLocalFile("fxml/dashboard/home.fxml")));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        homeDashboardController = loader.getController();
        homeDashboardController.initialize(null);

        return root;
    }

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public AppController getController() {
        return homeDashboardController;
    }

    @Override
    public Type getType() {
        return Type.DASHBOARD;
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
