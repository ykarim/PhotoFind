package ui.search;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.util.Bundle;

import java.io.IOException;

public class SearchScene {

    private Bundle<String> keyInputData;

    public SearchScene() {

    }

    public SearchScene(Bundle<String> keyInputData) {
        this.keyInputData = keyInputData;
    }

    public void start(Stage stage) {
        FXMLLoader searchScreenLoader = new FXMLLoader();
        searchScreenLoader.setLocation(getClass().getResource("../../res/fxml/search/searchScreen.fxml"));

        Parent root = null;

        try {
            root = searchScreenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            SearchController searchController = searchScreenLoader.getController();
            searchController.initialize(keyInputData);

            Scene scene = new Scene(root, 600, 480);
            stage.setScene(scene);
            stage.show();
        }
    }
}
