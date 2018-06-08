package org.photofind;

import javafx.application.Application;
import javafx.stage.Stage;
import org.photofind.controller.PictureDAOImpl;
import org.photofind.media.Picture;
import org.photofind.media.descriptors.Caption;
import org.photofind.media.descriptors.Description;
import org.photofind.ui.dashboard.DashboardScene;
import org.photofind.ui.util.SceneManager;
import org.photofind.util.FileImport;

import java.util.ArrayList;

public class PhotoFindApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        for (int i = 0; i <= 60; i++) {
            Picture picture = new Picture(FileImport.importLocalFile("img/f.png")); //if file path is wrong program crashes fix this
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
