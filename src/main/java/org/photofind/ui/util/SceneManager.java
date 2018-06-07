package org.photofind.ui.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SceneManager {

    private static Stage stage;
    private static Scene mainScene;
    private static AppScene currentScene;
    private static ArrayList<AppScene> scenes = new ArrayList<>();

    //TODO: Take in bundle in add() and initialize controller
    public static void initProgram(Stage stage, AppScene appScene) {
        SceneManager.stage = stage;
        SceneManager.mainScene = new Scene(appScene.start(), 600, 480);
        SceneManager.currentScene = appScene;

        //Add initial scene to scenes list
        scenes.add(appScene);
        appScene.setStarted(true);

        stage.setScene(mainScene);
        stage.show();
    }

    public static void addScene(AppScene scene) {
        scenes.add(scene);
        mainScene.setRoot(scene.start());
        //Find way to init controller
        scene.setStarted(true);
        currentScene = scene;
    }

    public static void returnToPreviousScene() {
        //Must return to element at (index = size-2) which represents previous scene
        if (scenes.size() > 2 && scenes.get(scenes.size() - 2) != null) {
            AppScene previousScene = scenes.get(scenes.size() - 2);
            if (previousScene.getStarted()) {
                mainScene.setRoot(previousScene.getParent());
                scenes.add(previousScene);
                currentScene = previousScene;
            }
        }
    }

    public static void returnToPreviousScene(int previousAmount) {
        if (scenes.size() > (previousAmount + 1) && scenes.get(scenes.size() - (previousAmount + 1)) != null) {
            AppScene previousScene = scenes.get(scenes.size() - (previousAmount + 1));
            if (previousScene.getStarted()) {
                mainScene.setRoot(previousScene.getParent());
                scenes.add(previousScene);
                currentScene = previousScene;
            }
        }
    }

    public static void returnToPreviousScene(AppScene.Type sceneType) {
        //Descending for loop
        for (int index = scenes.size() - 1; index >= 0; index--) {
            if (sceneType.equals(scenes.get(index).getType())) {
                AppScene scene = scenes.get(index);
                mainScene.setRoot(scene.getParent());
                scenes.add(scene);
                currentScene = scene;
            }
        }
    }

    public static void refreshCurrentScene() {
        currentScene.getController().refresh();
    }

}
