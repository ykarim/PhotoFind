package org.photofind.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.photofind.ui.dashboard.DashboardScene;
import org.photofind.ui.util.SceneManager;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        DashboardScene dashboardScene = new DashboardScene();
        SceneManager.initProgram(stage, dashboardScene);
    }
}
