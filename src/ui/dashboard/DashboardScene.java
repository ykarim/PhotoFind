package ui.dashboard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ui.util.AppController;
import ui.util.AppScene;

import java.io.IOException;

public class DashboardScene implements AppScene {

    private Parent root;
    private HomeDashboardController homeDashboardController;
    private boolean isStarted;

    @Override
    public Parent start() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../res/fxml/dashboard/home.fxml"));

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
