package ui.search;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchScene {

    public SearchScene() {

    }

    public void start(Stage stage) {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("../../res/fxml/search/searchScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            Scene scene = new Scene(root, 600, 480);
            stage.setScene(scene);
            stage.show();
        }
    }
}
