package org.photofind.ui.image_info;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.photofind.media.Picture;
import org.photofind.ui.imageDetails.ImageDetailsScene;
import org.photofind.ui.util.Bundle;
import org.photofind.ui.util.SceneManager;
import org.photofind.util.FileImport;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ImageBox extends VBox {

    @FXML
    private Label label_filename;

    @FXML
    private Label label_editdate;

    @FXML
    private AnchorPane anchorPane_imageHolder;

    @FXML
    private ImageView imageview_image;

    @FXML
    private HBox hBox_imageHeader;

    private Picture picture;

    public ImageBox() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(FileImport.getFileURL(FileImport.importLocalFile("fxml/imageBox/imageBox.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception); //Catching and printing produces error and distorts shape
        }
    }

    public Label getLabel_filename() {
        return label_filename;
    }

    public Label getLabel_editdate() {
        return label_editdate;
    }

    public AnchorPane getAnchorPane_imageHolder() {
        return anchorPane_imageHolder;
    }

    public ImageView getImageview_image() {
        return imageview_image;
    }

    //Maybe set in constructor as cant exist without constructor
    public void setPicture(Picture picture) {
        this.picture = picture;

        imageview_image.setImage(FileImport.importImage(picture));

        imageview_image.setPreserveRatio(false);
        imageview_image.setSmooth(true);

//        imageview_image.fitHeightProperty().bind(anchorPane_imageHolder.heightProperty());
        imageview_image.fitWidthProperty().bind(hBox_imageHeader.widthProperty()); //hacky and why?

        label_filename.setText(picture.getName());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        label_editdate.setText(sdf.format(picture.getFile().lastModified()));
    }

    @FXML
    public void handleActionEvent(MouseEvent event) {
        openImageDetailsScene();
    }

    private void openImageDetailsScene() {
        Bundle<Picture> pictureBundle = new Bundle<>(picture);
        ImageDetailsScene imageDetailsScene = new ImageDetailsScene(pictureBundle);
        SceneManager.addScene(imageDetailsScene);
    }
}
