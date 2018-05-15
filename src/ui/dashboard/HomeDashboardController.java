package ui.dashboard;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import ui.search.SearchScene;

import java.net.URL;

public class HomeDashboardController {

    @FXML
    private JFXTextField textField_searchBar;

    @FXML
    private Button button_search;

    @FXML
    private Button button_upload;

    @FXML
    private ImageView imageview_image;

    @FXML
    private AnchorPane anchorPane_imageHolder;

    @FXML
    private VBox vBox_imageBoxOne;

    @FXML
    private VBox vBox_imageBoxTwo;

    @FXML
    private VBox vBox_imageBoxThree;

    @FXML
    private URL location;

    public HomeDashboardController() {

    }

    public void initialize() {
        getImageview_image().fitHeightProperty().bind(getAnchorPane_imageHolder().heightProperty());
        getImageview_image().fitWidthProperty().bind(getAnchorPane_imageHolder().widthProperty());

        vBox_imageBoxOne.setVisible(false);
        vBox_imageBoxTwo.setVisible(false);
        vBox_imageBoxThree.setVisible(false);
    }

    @FXML
    protected void handleSearchBarKeyTyped(KeyEvent event) {
        openSearchScene(((Node) event.getTarget()).getScene().getWindow());
    }

    @FXML
    protected void handleSearchButtonAction(ActionEvent event) {
        openSearchScene(((Node) event.getTarget()).getScene().getWindow());
    }

    @FXML
    protected void handleUploadButtonAction(ActionEvent event) {

    }

    public Button getButton_upload() {
        return button_upload;
    }

    public ImageView getImageview_image() {
        return imageview_image;
    }

    public AnchorPane getAnchorPane_imageHolder() {
        return anchorPane_imageHolder;
    }

    private void openSearchScene(Window currentWindow) {
        SearchScene scene = new SearchScene();
        scene.start((Stage) currentWindow);
    }
}
