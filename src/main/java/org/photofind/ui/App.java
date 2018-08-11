package org.photofind.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.photofind.net.Subscription;
import org.photofind.preferences.PreferencesStore;
import org.photofind.ui.dashboard.DashboardScene;
import org.photofind.ui.settings.SettingsScene;
import org.photofind.ui.util.SceneManager;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        //Load program preferences
        PreferencesStore.loadProgramPreferences();

        DashboardScene dashboardScene = new DashboardScene();
        SceneManager.initProgram(stage, dashboardScene);

        if (Subscription.getSubscriptionKey().isEmpty()) {
            SceneManager.addScene(new SettingsScene());
        }
    }
}
