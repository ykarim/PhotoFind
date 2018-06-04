package ui.util;

import javafx.scene.Parent;

public interface AppScene {

    Parent start();

    Parent getParent();

    AppController getController();

    Type getType();

    boolean getStarted();

    void setStarted(boolean isStarted);

    enum Type {
        ADDEDIT, DASHBOARD, IMAGEDETAILS, SEARCH, SETTINGS
    }
}
