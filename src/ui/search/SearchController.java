package ui.search;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import controller.PictureDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import media.Picture;
import ui.image_info.ImageBox;

import java.util.ArrayList;

public class SearchController {

    @FXML
    protected JFXTextField textField_searchBar;
    @FXML
    protected JFXButton button_searchButton;
    @FXML
    protected JFXMasonryPane masonryPane_searchResults;
    @FXML
    protected JFXSpinner spinner_resultSpinner;
    //Android-like system
    private PictureDAOImpl pictureDAO = new PictureDAOImpl();
    private ArrayList<Picture> pictures = new ArrayList<>();

    public SearchController() {

    }

    @FXML
    protected void handleSearchBarKeyTyped(KeyEvent event) {
        searchImagesByTag();
    }

    @FXML
    protected void handleSearchButtonAction(ActionEvent event) {
        searchImagesByTag();
    }

    void initialize() {
        masonryPane_searchResults.cellHeightProperty().bind(new ImageBox().heightProperty());
        masonryPane_searchResults.cellWidthProperty().bind(new ImageBox().widthProperty());
    }

    private void searchImagesByTag() {
        spinner_resultSpinner.setVisible(true);

        ArrayList<Picture> tagSearchResults = pictureDAO.getPicturesByTagName(textField_searchBar.getText().trim());
        setMasonryPanePictures(tagSearchResults);

        spinner_resultSpinner.setVisible(false);
    }

    private void setMasonryPanePictures(ArrayList<Picture> pictures) {
        if (pictures != null) {
            ArrayList<ImageBox> imageBoxes = new ArrayList<>();

            for (Picture picture : pictures) {
                ImageBox imageBox = new ImageBox();
                imageBox.setPicture(picture);
                imageBoxes.add(imageBox);
            }

            masonryPane_searchResults.getChildren().addAll(imageBoxes);
        }
    }
}
