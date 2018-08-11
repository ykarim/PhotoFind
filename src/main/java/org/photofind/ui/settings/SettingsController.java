package org.photofind.ui.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import org.photofind.net.Subscription;
import org.photofind.preferences.PreferencesStore;
import org.photofind.ui.util.AppController;
import org.photofind.ui.util.Bundle;
import org.photofind.ui.util.SceneManager;

public class SettingsController implements AppController {

    @FXML
    private JFXRadioButton radio_msvision;

    @FXML
    private JFXRadioButton radio_gcvision;

    @FXML
    private JFXTextField textField_key;

    @FXML
    private JFXButton button_submit;

    private ToggleGroup toggle_visionProvider = new ToggleGroup();

    @Override
    public void initialize(Bundle dataBundle) {
        textField_key.setText(Subscription.getSubscriptionKey());

        textField_key.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textField_key.validate();
            }
        });

        radio_msvision.setToggleGroup(toggle_visionProvider);
        radio_gcvision.setToggleGroup(toggle_visionProvider);

        switch (Subscription.getCurrentSubscriptionProvider()) {

            case GOOGLE_CLOUD:
                radio_gcvision.setSelected(true);
                break;
            case MICROSOFT_VISION:
                radio_msvision.setSelected(true);
                break;
        }
    }

    @FXML
    protected void handleBackButtonAction(ActionEvent event) {
        if (validateInput()) {
            returnToPreviousScene();
        }
    }

    @FXML
    protected void handleSubmitAction(ActionEvent event) {
        String subKey = textField_key.getText().trim();

        if (validateInput()) {
            Subscription.setSubscriptionKey(subKey);

            if (toggle_visionProvider.getSelectedToggle().equals(radio_msvision)) {
                Subscription.setCurrentSubscriptionProvider(Subscription.Provider.MICROSOFT_VISION);
            } else if (toggle_visionProvider.getSelectedToggle().equals(radio_gcvision)) {
                Subscription.setCurrentSubscriptionProvider(Subscription.Provider.GOOGLE_CLOUD);
            }

            returnToPreviousScene();
            PreferencesStore.saveProgramPreferences();
        }
    }

    private boolean validateInput() {
        boolean validInput = true;

        if (textField_key.getText().isEmpty()) {
            validInput = false;
        }

        return validInput;
    }

    private void returnToPreviousScene() {
        SceneManager.returnToPreviousScene();
    }

    @Override
    public void refresh() {
        textField_key.setText(Subscription.getSubscriptionKey());
    }
}
