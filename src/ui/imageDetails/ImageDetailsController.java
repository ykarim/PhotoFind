package ui.imageDetails;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import media.Picture;
import media.descriptors.Caption;
import media.descriptors.Tag;
import ui.addEditImage.AddEditImageScene;
import ui.util.Bundle;
import util.FileImport;

import java.util.ArrayList;
import java.util.List;

public class ImageDetailsController {

    @FXML
    private Button button_backButton;

    @FXML
    private Button button_deleteButton;

    @FXML
    private AnchorPane anchorPane_imageHolder;

    @FXML
    private ImageView imageView_image;

    @FXML
    private Label label_nameLabel;

    @FXML
    private Label label_tagsLabel;

    @FXML
    private Label label_captionsLabel;

    private Scene previousScene;
    private Picture currentPicture;

    void initialize(Scene previousScene, Bundle<Picture> pictureBundle) {
        this.previousScene = previousScene;
        this.currentPicture = pictureBundle.getData();

        //Set sizing of imageView to fill anchorPane
        imageView_image.fitHeightProperty().bind(anchorPane_imageHolder.heightProperty());
        imageView_image.fitWidthProperty().bind(anchorPane_imageHolder.widthProperty());

        //Populate fields with picture data
        imageView_image.setImage(FileImport.importImage(currentPicture));
        label_nameLabel.setText(currentPicture.getName());
        label_tagsLabel.setText(generateTagString(currentPicture.getTags()));
        label_captionsLabel.setText(generateCaptionsString(currentPicture.getDescription().getCaptions()));
    }

    @FXML
    protected void handleBackButtonAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        currentStage.setScene(previousScene);
        currentStage.show();
    }

    @FXML
    protected void handleEditAction(ActionEvent event) {
        openEditImageScene(event);
    }

    private String generateTagString(ArrayList<Tag> tags) {
        StringBuilder tagString = new StringBuilder();

        for (Tag tag : tags) {
            tagString.append(tag.getName())
                    .append(" - ")
                    .append(tag.getConfidence())
                    .append(", ");
        }

        if (tags.size() != 0) {
            tagString.delete(tagString.lastIndexOf(", "), tagString.length() - 1);
        }
        return tagString.toString();
    }

    private String generateCaptionsString(ArrayList<Caption> captions) {
        StringBuilder captionsString = new StringBuilder();

        for (Caption caption : captions) {
            captionsString.append(caption.getText())
                    .append(" - ")
                    .append(caption.getConfidence())
                    .append(", ");
        }

        if (captions.size() != 0) {
            captionsString.delete(captionsString.lastIndexOf(", "), captionsString.length() - 1);
        }
        return captionsString.toString();
    }

    private void openEditImageScene(Event event) {
        Scene currentScene = ((Node) event.getTarget()).getScene();
        Window currentWindow = currentScene.getWindow();
        List<Object> scenePictureData = new ArrayList<>();
        scenePictureData.add(previousScene);
        scenePictureData.add(currentPicture);
        AddEditImageScene editImageScene = new AddEditImageScene(AddEditImageScene.Mode.EDIT,
                new Bundle<>(scenePictureData));

        editImageScene.start(currentScene, (Stage) currentWindow);
    }
}
