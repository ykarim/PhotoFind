package ui.search;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ui.util.AppController;
import ui.util.AppScene;
import ui.util.Bundle;

import java.io.IOException;

public class SearchScene implements AppScene {

    private Bundle<String> keyInputData;
    private Parent root;
    private SearchController searchController;
    private boolean isStarted;

    public SearchScene() {

    }

    public SearchScene(Bundle<String> keyInputData) {
        this.keyInputData = keyInputData;
    }

    @Override
    public Parent start() {
        FXMLLoader searchScreenLoader = new FXMLLoader();
        searchScreenLoader.setLocation(getClass().getResource("../../res/fxml/search/search.fxml"));

        try {
            root = searchScreenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            searchController = searchScreenLoader.getController();
            searchController.initialize(keyInputData);
        }

        return root;
    }

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public AppController getController() {
        return searchController;
    }

    @Override
    public Type getType() {
        return Type.SEARCH;
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
