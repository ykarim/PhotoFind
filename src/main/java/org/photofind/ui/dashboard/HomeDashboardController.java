package org.photofind.ui.dashboard;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.photofind.ui.addEditImage.AddEditImageScene;
import org.photofind.ui.search.SearchScene;
import org.photofind.ui.settings.SettingsScene;
import org.photofind.ui.util.AppController;
import org.photofind.ui.util.Bundle;
import org.photofind.ui.util.SceneManager;

import java.io.File;
import java.net.URL;
import java.util.List;

public class HomeDashboardController implements AppController {

    @FXML
    private AnchorPane anchorPane_content;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private VBox vBox_sidemenu;

    @FXML
    private HBox hBox_search;

    @FXML
    private VBox vBox_images;

    @FXML
    private HBox hBox_upload;

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

    @Override
    public void initialize(Bundle dataBundle) {
        //Set Hamburger animation
        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamburger);
        transition.setRate(-1);
        hamburger.setAnimation(transition);

        getImageview_image().fitHeightProperty().bind(getAnchorPane_imageHolder().heightProperty());
        getImageview_image().fitWidthProperty().bind(getAnchorPane_imageHolder().widthProperty());

        //Prevent entry into searchBar as all input will be relayed to SearchScene
        textField_searchBar.textProperty().bind(new SimpleStringProperty());

        vBox_imageBoxOne.setVisible(false);
        vBox_imageBoxTwo.setVisible(false);
        vBox_imageBoxThree.setVisible(false);
    }

    @FXML
    protected void handleHamburgerPress(MouseEvent event) {
        //Invert and play hamburger animation
        Transition transition = hamburger.getAnimation();
        transition.setRate(transition.getRate() * -1);
        transition.play();

        //Play sideMenu animation depending on current visibility state
        double startWidth = vBox_sidemenu.getWidth();
        final Animation hideSidemenu = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }

            protected void interpolate(double frac) {
                final double curWidth = startWidth * (1.0 - frac);
                vBox_sidemenu.setMinWidth(curWidth);
                vBox_sidemenu.setTranslateX(-startWidth + curWidth);
            }
        };
        hideSidemenu.onFinishedProperty().set(actionEvent -> vBox_sidemenu.setVisible(false));

        final Animation showSidemenu = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }

            protected void interpolate(double frac) {
                final double curWidth = startWidth * frac;
                vBox_sidemenu.setMinWidth(curWidth);
                vBox_sidemenu.setTranslateX(-startWidth + curWidth);
            }
        };

        //Known bug where multiple rapid clicks of hamburger mess up animations. Closed Hamburger is shown with menu visible
        if (showSidemenu.statusProperty().get() == Animation.Status.STOPPED && hideSidemenu.statusProperty().get() == Animation.Status.STOPPED) {
            if (vBox_sidemenu.isVisible()) {
                hideSidemenu.play();
            } else {
                vBox_sidemenu.setVisible(true);
                showSidemenu.play();
            }
        }
    }

    @FXML
    protected void handleMenuSettingsAction(ActionEvent event) {
        openSettingsScene();
    }

    @FXML
    protected void handleSearchBarKeyTyped(KeyEvent event) {
        openSearchScene(event);
    }

    @FXML
    protected void handleSearchButtonAction(ActionEvent event) {
        openSearchScene(event);
    }

    @FXML
    protected void handleUploadButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Picture File");

        //Chosen using Cognitive Services accepted file formats
        FileChooser.ExtensionFilter pictureExtensionFilter =
                new FileChooser.ExtensionFilter("Image",
                        "*.png",
                        "*.jpg",
                        "*.jpeg",
                        "*.gif",
                        "*.bmp");
        fileChooser.getExtensionFilters().add(pictureExtensionFilter);
        List<File> files = fileChooser.showOpenMultipleDialog(((Node) event.getTarget()).getScene().getWindow());

        if (files != null && files.size() > 0) {
            openAddImageScene(files);
        }
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

    private void openSearchScene(Event event) {
        SearchScene searchScene;

        if (event instanceof KeyEvent) {
            KeyEvent keyEvent = ((KeyEvent) event);
            String inputText = keyEvent.getCharacter();//mb try reading val of textfield //if copy paste

            Bundle<String> keyInputData = new Bundle<>(inputText);
            searchScene = new SearchScene(keyInputData);
        } else {
            searchScene = new SearchScene();
        }

        SceneManager.addScene(searchScene);
    }

    private void openAddImageScene(List<File> files) {
        AddEditImageScene addEditImageScene = new AddEditImageScene(AddEditImageScene.Mode.ADD,
                new Bundle<>(files));
        SceneManager.addScene(addEditImageScene);
    }

    private void openSettingsScene() {
        SettingsScene settingsScene = new SettingsScene();
        SceneManager.addScene(settingsScene);
    }

    @Override
    public void refresh() {

    }
}
