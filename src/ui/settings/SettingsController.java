package ui.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.Subscription;

public class SettingsController {

    @FXML
    private JFXTextField textField_key;

    @FXML
    private JFXButton button_submit;

    private Scene previousScene;

    void initialize(Scene previousScene) {
        this.previousScene = previousScene;

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
        Stage currentStage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        currentStage.setScene(previousScene);
        currentStage.show();
    }
}
