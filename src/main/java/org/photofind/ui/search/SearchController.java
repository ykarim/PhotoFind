package org.photofind.ui.search;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.photofind.controller.PictureDAOImpl;
import org.photofind.media.Picture;
import org.photofind.ui.image_info.ImageBox;
import org.photofind.ui.util.AppController;
import org.photofind.ui.util.AppScene;
import org.photofind.ui.util.Bundle;
import org.photofind.ui.util.SceneManager;

import java.util.ArrayList;

public class SearchController implements AppController {

    @FXML
    protected JFXTextField textField_searchBar;
    @FXML
    protected JFXButton button_searchButton;
    //    @FXML
//    protected JFXMasonryPane masonryPane_searchResults;
    @FXML
    protected GridPane gridPane;
    @FXML
    protected JFXSpinner spinner_resultSpinner;

    private PictureDAOImpl pictureDAO = new PictureDAOImpl();

    public SearchController() {

    }

    @Override
    public void initialize(Bundle dataBundle) {
        if (dataBundle != null && dataBundle.getData() != null && dataBundle.getData() instanceof String) {
            Bundle<String> inputKeyBundle = (Bundle<String>) dataBundle;
            String inputText = inputKeyBundle.getData();

            //Set text later as it must come after initialization otherwise text will be highlighted upon scene creation
            Platform.runLater(() -> {
                textField_searchBar.requestFocus();
                textField_searchBar.setText(inputText);
                textField_searchBar.positionCaret(inputText.length());

                //Perform search action on given inputText
                //Placed under runLater as otherwise searchBar won't have value
                searchImagesByTag();
            });
        }
    }

    //Known issue where extra char is sometimes needed to start search
    @FXML
    protected void handleSearchBarKeyTyped(KeyEvent event) {
        searchImagesByTag();
    }

    @FXML
    protected void handleSearchButtonAction(ActionEvent event) {
        searchImagesByTag();
    }

    @FXML
    protected void handleBackButtonAction(ActionEvent event) {
        SceneManager.returnToPreviousScene(AppScene.Type.DASHBOARD);
    }

    //known issue with lag after typing last character "y" where lastchar wouldnt show up till search complete
    private void searchImagesByTag() {
        gridPane.getChildren().clear();
        setLoading(true);

        //Separate Thread to search for pictures and create ImageBox objects
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Picture> tagSearchResults = pictureDAO.getPicturesByTagName(textField_searchBar.getText().trim());
                setMasonryPanePictures(tagSearchResults);
            }
        }).start();
    }

    //Known Issue where if position is manually set to center imageview stays left while hbox centers
    //Known issue with sizing and height of box not changing visibly - is this issue tho or expected behavior?
    private void setMasonryPanePictures(ArrayList<Picture> pictures) {
        if (pictures != null && !pictures.isEmpty()) {
            ArrayList<ImageBox> imageBoxes = new ArrayList<>();

            //Should inc. under runLater as loading GlyphIcon must be under FX thread and gives error when done differently
            for (Picture picture : pictures) {
                ImageBox imageBox = new ImageBox();
                imageBox.setPicture(picture);
                imageBoxes.add(imageBox);
            }

            Platform.runLater(() -> {
                int currentColumn = 0;
                int currentRow = 0;

                for (ImageBox imageBox : imageBoxes) {
                    if (currentColumn < 3) {
                        gridPane.add(imageBox, currentColumn, currentRow);
                        currentColumn += 1;
                    } else {
                        currentColumn = 1;
                        currentRow += 1;
                        gridPane.add(imageBox, 0, currentRow);
                    }
                }
            });
//            masonryPane_searchResults.getChildren().addAll(imageBoxes);
        }

        setLoading(false);
    }

    private void setLoading(boolean value) {
        Platform.runLater(() -> spinner_resultSpinner.setVisible(value));
    }

    @Override
    public void refresh() {
        searchImagesByTag();
    }
}
