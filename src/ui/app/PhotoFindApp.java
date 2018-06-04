package ui.app;

import controller.PictureDAOImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import media.Picture;
import media.descriptors.Caption;
import media.descriptors.Description;
import ui.dashboard.DashboardScene;
import ui.util.SceneManager;
import util.FileImport;

import java.util.ArrayList;

public class PhotoFindApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        for (int i = 0; i <= 60; i++) {
            Picture picture = new Picture(FileImport.importLocalFile("../res/img/f.png")); //if file path is wrong program crashes fix this
            picture.addTag("TOY", 1);

            ArrayList<Caption> captions = new ArrayList<>();

            for (int c = 0; c <= 3; c++) {
                captions.add(new Caption("Test", 1.0));
            }

            picture.setDescription(new Description(captions, null));
            new PictureDAOImpl().addPicture(picture);
        }

        DashboardScene dashboardScene = new DashboardScene();
        SceneManager.initProgram(stage, dashboardScene);
    }
}
