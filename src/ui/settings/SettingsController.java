package ui.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import net.Subscription;
import ui.util.AppController;
import ui.util.Bundle;
import ui.util.SceneManager;

public class SettingsController implements AppController {

    @FXML
    private JFXTextField textField_key;

    @FXML
    private JFXButton button_submit;

    @Override
    public void initialize(Bundle dataBundle) {
        textField_key.setText(Subscription.getSubscriptionKey());

        textField_key.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textField_key.validate();
            }
        });
    }

    @FXML
    protected void handleBackButtonAction(ActionEvent event) {
        returnToPreviousScene(event);
    }

    @FXML
    protected void handleSubmitAction(ActionEvent event) {
        String subKey = textField_key.getText().trim();

        if (validateInput()) {
            Subscription.setSubscriptionKey(subKey);
            returnToPreviousScene(event);
        }
    }

    private boolean validateInput() {
        boolean validInput = true;

        if (textField_key.getText().isEmpty()) {
            validInput = false;
        }

        return validInput;
    }

    private void returnToPreviousScene(Event event) {
        SceneManager.returnToPreviousScene();
    }

    @Override
    public void refresh() {
        textField_key.setText(Subscription.getSubscriptionKey());
    }
}
