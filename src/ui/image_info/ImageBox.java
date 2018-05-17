package ui.image_info;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import media.Picture;

import java.io.IOException;

public class ImageBox extends VBox {

    @FXML
    protected Label label_filename;

    @FXML
    protected Label label_editdate;

    @FXML
    protected AnchorPane anchorPane_imageHolder;

    @FXML
    protected ImageView imageview_image;

    @FXML
    protected HBox hBox_imageHeader;

    public ImageBox() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("../../res/fxml/image_info/image_box.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
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

    public void setPicture(Picture picture) {
        imageview_image.setImage(new Image("file:" + picture.getFile().getPath()));

        imageview_image.setPreserveRatio(false);
        imageview_image.setSmooth(true);

//        imageview_image.fitHeightProperty().bind(anchorPane_imageHolder.heightProperty());
        imageview_image.fitWidthProperty().bind(hBox_imageHeader.widthProperty()); //hacky and why?

        label_filename.setText(picture.getFile().getName());
        label_editdate.setText(Long.toString(picture.getFile().lastModified()));
    }
}
