package ui.addEditImage;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.DoubleTextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import controller.PictureDAOImpl;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import media.Picture;
import media.descriptors.Caption;
import media.descriptors.Tag;
import process.VisionAnalysisProcess;
import ui.model.UICaption;
import ui.model.UITag;
import ui.util.AppController;
import ui.util.Bundle;
import ui.util.SceneManager;
import util.FileImport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddEditImageController implements AppController {

    @FXML
    private StackPane stackPane_content;

    @FXML
    private AnchorPane anchorPane_content;

    @FXML
    private JFXSpinner spinner_status;

    @FXML
    private Label label_title;

    @FXML
    private JFXButton button_backButton;

    @FXML
    private JFXButton button_leftPage;

    @FXML
    private JFXButton button_rightPage;

    @FXML
    private AnchorPane anchorPane_imageHolder;

    @FXML
    private ImageView imageView_image;

    @FXML
    private JFXTextField textField_pictureName;

    @FXML
    private JFXButton button_addTag;

    @FXML
    private JFXButton button_removeTag;

    @FXML
    private JFXTreeTableView<UITag> tableView_tags;

    @FXML
    private JFXTreeTableColumn<UITag, String> tableColumn_tagName;

    @FXML
    private JFXTreeTableColumn<UITag, Double> tableColumn_tagConfidence;

    @FXML
    private JFXButton button_addCaption;

    @FXML
    private JFXButton button_removeCaption;

    @FXML
    private JFXTreeTableView<UICaption> tableView_captions;

    @FXML
    private JFXTreeTableColumn<UICaption, String> tableColumn_captionText;

    @FXML
    private JFXTreeTableColumn<UICaption, Double> tableColumn_captionConfidence;

    private AddEditImageScene.Mode mode;
    private PictureDAOImpl pictureDAO = new PictureDAOImpl();
    private ArrayList<Picture> pictures = new ArrayList<>();
    private ObservableList<UITag> currentPictureTags;
    private ObservableList<UICaption> currentPictureCaptions;

    private SimpleIntegerProperty currentPictureIndex = new SimpleIntegerProperty(0);

    private VisionAnalysisProcess visionAnalysisProcess;

    public AddEditImageController() {

    }

    @Override
    public void initialize(Bundle dataBundle) {
        imageView_image.fitHeightProperty().bind(anchorPane_imageHolder.heightProperty());
        imageView_image.fitWidthProperty().bind(anchorPane_imageHolder.widthProperty());

        if (mode.equals(AddEditImageScene.Mode.ADD) && dataBundle.getData() instanceof List) {
            Bundle<List<File>> filesBundle = (Bundle<List<File>>) dataBundle;
            for (File file : filesBundle.getData()) {
                Picture newPicture = new Picture(file);
                pictures.add(newPicture);
            }

            imageView_image.setImage(FileImport.importImage(pictures.get(currentPictureIndex.get())));
            textField_pictureName.setText(pictures.get(currentPictureIndex.get()).getName());

            setAddTitleText();
        } else if (mode.equals(AddEditImageScene.Mode.EDIT) && dataBundle.getData() instanceof Picture) {
            Bundle<Picture> pictureBundle = (Bundle<Picture>) dataBundle;
            Picture picture = pictureBundle.getData();
            pictures.add(picture);

            imageView_image.setImage(FileImport.importImage(picture));
            textField_pictureName.setText(picture.getName());

            setEditTitleText();
        }

        setupButtons();
        setupTagsTableView();
        setupCaptionsTableView();
    }

    @Override
    public void refresh() {

    }

    public void setMode(AddEditImageScene.Mode mode) {
        this.mode = mode;
    }

    @FXML
    protected void handleBackButtonAction(ActionEvent event) {
        returnToPreviousScene();
    }

    @FXML
    protected void handleLeftPageButtonAction(ActionEvent event) {
        savePicture();
        currentPictureIndex.set(currentPictureIndex.get() - 1);
        refreshPage();
    }

    @FXML
    protected void handleRightPageButtonAction(ActionEvent event) {
        savePicture();
        currentPictureIndex.set(currentPictureIndex.get() + 1);
        refreshPage();
    }

    @FXML
    protected void handleAddTagAction(ActionEvent event) {
        currentPictureTags.add(new UITag("", Double.NaN));

        //Set focus on textField within newly created row
        tableView_tags.requestFocus();
        tableView_tags.getSelectionModel().select(currentPictureTags.size() - 1);
        tableView_tags.getFocusModel().focus(currentPictureTags.size() - 1, tableColumn_tagName);
        tableView_tags.edit(currentPictureTags.size() - 1, tableColumn_tagName);
    }

    @FXML
    protected void handleRemoveTagAction(ActionEvent event) {
        currentPictureTags.remove(tableView_tags.getSelectionModel().selectedIndexProperty().get());
        //Must clear or else item will remain selected
        tableView_tags.getSelectionModel().clearSelection();
    }

    @FXML
    protected void handleAddCaptionAction(ActionEvent event) {
        currentPictureCaptions.add(new UICaption(""));

        //Set focus on textField within newly created row
        tableView_captions.requestFocus();
        tableView_captions.getSelectionModel().select(currentPictureCaptions.size() - 1);
        tableView_captions.getFocusModel().focus(currentPictureCaptions.size() - 1, tableColumn_captionText);
        tableView_captions.edit(currentPictureCaptions.size() - 1, tableColumn_captionText);
    }

    @FXML
    protected void handleRemoveCaptionAction(ActionEvent event) {
        currentPictureCaptions.remove(tableView_captions.getSelectionModel().selectedIndexProperty().get());
        //Must clear or else item will remain selected
        tableView_captions.getSelectionModel().clearSelection();
    }

    @FXML
    //If in edit mode should this delete picture? TODO
    protected void handleDeleteButtonAction(ActionEvent event) {
        //Remove picture at current index, shifting the other pictures down
        pictures.remove(currentPictureIndex.get());

        //After removing element, check if current index exceeds picture size due to removing last element
        if (currentPictureIndex.get() != pictures.size()) {
            //Rebind buttons
            setupButtons();

            //Refresh Page
            refreshPage();
        } else if (currentPictureIndex.get() == pictures.size()) {
            //If current index is out of bounds of pictures list, check if no pictures remain
            if (currentPictureIndex.get() == 0) {
                //If no pictures remain, return to homeDashboard
                returnToPreviousScene();
            } else {
                //If pictures still remain, subtract 1 from index and refresh page
                currentPictureIndex.set(currentPictureIndex.get() - 1);
                setupButtons();
                refreshPage();
            }
        }
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        //Dialog content
        JFXDialogLayout layout = new JFXDialogLayout();
        Label dialogHeading = new Label("Confirm");
        layout.setHeading(dialogHeading);

        String dialogText = "";
        if (mode.equals(AddEditImageScene.Mode.ADD)) {
            if (pictures.size() == 1) {
                dialogText = "Are you sure you want to add this picture?";
            } else {
                dialogText = "Are you sure you want to add all these pictures?";
            }
        } else if (mode.equals(AddEditImageScene.Mode.EDIT)) {
            dialogText = "Are you sure you want to edit this picture?";
        }

        Label dialogBody = new Label(dialogText);
        layout.setBody(dialogBody);
        JFXButton yesButton = new JFXButton("YES");
        yesButton.getStyleClass().add("dialog-accept");
        JFXButton noButton = new JFXButton("NO");
        noButton.getStyleClass().add("dialog-reject");
        layout.setActions(noButton, yesButton);

        //Show dialog
        JFXDialog confirmationDialog = new JFXDialog(stackPane_content, layout, JFXDialog.DialogTransition.CENTER);
        confirmationDialog.show(stackPane_content);

        //Set listeners
        yesButton.setOnAction(yesButtonEvent -> {
            if (mode.equals(AddEditImageScene.Mode.ADD)) {
                //Current picture hasn't been saved yet so save
                savePicture();
                confirmationDialog.close();

                startPictureAnalysis(yesButtonEvent);
            } else if (mode.equals(AddEditImageScene.Mode.EDIT)) {
                //Picture is sent as reference to picture in DAO so saving here will update picture in DAO
                savePicture();
                confirmationDialog.close();
                openRefreshedImageDetailsScene();
            }
        });

        noButton.setOnAction(noButtonEvent -> confirmationDialog.close());
    }

    private void setAddTitleText() {
        label_title.setText("Add Image (" + (currentPictureIndex.get() + 1) + " of " + pictures.size() + ")");
    }

    private void setEditTitleText() {
        label_title.setText("Edit Image"); //chg to picture for consistency
    }

    private void setupButtons() {
        button_leftPage.visibleProperty().bind(
                Bindings.notEqual(0, currentPictureIndex));
        button_rightPage.visibleProperty().bind(
                Bindings.notEqual(pictures.size() - 1, currentPictureIndex));

        button_removeTag.disableProperty().bind(
                Bindings.equal(-1, tableView_tags.getSelectionModel().selectedIndexProperty()));
        button_removeCaption.disableProperty().bind(
                Bindings.equal(-1, tableView_captions.getSelectionModel().selectedIndexProperty()));
    }

    private void setupTagsTableView() {
        tableColumn_tagName.setCellValueFactory((TreeTableColumn.CellDataFeatures<UITag, String> param) -> {
            if (tableColumn_tagName.validateValue(param)) {
                return new SimpleStringProperty(param.getValue().getValue().getName());
            } else {
                return tableColumn_tagName.getComputedValue(param);
            }
        });

        tableColumn_tagConfidence.setCellValueFactory(param -> {
            if (tableColumn_tagConfidence.validateValue(param)) {
                return new SimpleDoubleProperty(param.getValue().getValue().getConfidence()).asObject();
            } else {
                return tableColumn_tagConfidence.getComputedValue(param);
            }
        });

        tableColumn_tagName.setCellFactory(param -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        tableColumn_tagName.setOnEditCommit((TreeTableColumn.CellEditEvent<UITag, String> t) ->
                t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()
                        .setName(t.getNewValue()));

        tableColumn_tagConfidence.setCellFactory(param ->
                new GenericEditableTreeTableCell<>(new DoubleTextFieldEditorBuilder())); //maybe extend textfield to set requirements of entry like cant be empty or put listener in oneditcommit
        tableColumn_tagConfidence.setOnEditCommit((TreeTableColumn.CellEditEvent<UITag, Double> t) ->
                t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()
                        .setConfidence(t.getNewValue()));

        currentPictureTags = FXCollections.observableArrayList(convertTagsListToUITags(currentPictureIndex.get()));
        final TreeItem<UITag> root = new RecursiveTreeItem<UITag>(currentPictureTags, RecursiveTreeObject::getChildren);

        tableView_tags.setRoot(root);
        tableView_tags.setShowRoot(false);
    }

    private void setupCaptionsTableView() {
        tableColumn_captionText.setCellValueFactory((TreeTableColumn.CellDataFeatures<UICaption, String> param) -> {
            if (tableColumn_captionText.validateValue(param)) {
                return new SimpleStringProperty(param.getValue().getValue().getText());
            } else {
                return tableColumn_captionText.getComputedValue(param);
            }
        });

        tableColumn_captionConfidence.setCellValueFactory(param -> {
            if (tableColumn_captionConfidence.validateValue(param)) {
                return new SimpleDoubleProperty(param.getValue().getValue().getConfidence()).asObject();
            } else {
                return tableColumn_captionConfidence.getComputedValue(param);
            }
        });

        tableColumn_captionText.setCellFactory(param -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        tableColumn_captionText.setOnEditCommit((TreeTableColumn.CellEditEvent<UICaption, String> t) ->
                t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()
                        .setText(t.getNewValue()));

        tableColumn_captionConfidence.setCellFactory(param ->
                new GenericEditableTreeTableCell<>(new DoubleTextFieldEditorBuilder()));
        tableColumn_captionConfidence.setOnEditCommit((TreeTableColumn.CellEditEvent<UICaption, Double> t) ->
                t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()
                        .setConfidence(t.getNewValue()));

        currentPictureCaptions = FXCollections.observableArrayList(convertCaptionsListToUICaptions(currentPictureIndex.get()));
        final TreeItem<UICaption> root = new RecursiveTreeItem<UICaption>(currentPictureCaptions, RecursiveTreeObject::getChildren);

        tableView_captions.setRoot(root);
        tableView_captions.setShowRoot(false);
    }

    //Very sloppy code and inefficient
    //mb take in list and chg name
    private ArrayList<UITag> convertTagsListToUITags(int index) {
        ArrayList<UITag> uiTags = new ArrayList<>();

        for (Tag tag : pictures.get(index).getTags()) {
            uiTags.add(new UITag(tag));
        }

        return uiTags;
    }

    private ArrayList<Tag> convertUITagsListToTags() {
        ArrayList<Tag> tags = new ArrayList<>();

        for (UITag uiTag : currentPictureTags) {
            tags.add(uiTag.getTag());
        }

        return tags;
    }

    private ArrayList<UICaption> convertCaptionsListToUICaptions(int index) {
        ArrayList<UICaption> uiCaptions = new ArrayList<>();

        for (Caption caption : pictures.get(index).getDescription().getCaptions()) {
            uiCaptions.add(new UICaption(caption));
        }

        return uiCaptions;
    }

    private ArrayList<Caption> convertUICaptionsListToCaptions() {
        ArrayList<Caption> captions = new ArrayList<>();

        for (UICaption uiCaption : currentPictureCaptions) {
            captions.add(uiCaption.getCaption());
        }

        return captions;
    }

    private void savePicture() {
        Picture currentPicture = pictures.get(currentPictureIndex.get());

        currentPicture.setName(textField_pictureName.getText().trim());
        currentPicture.setTags(convertUITagsListToTags());
        currentPicture.getDescription().setCaptions(convertUICaptionsListToCaptions());
    }

    private void refreshPage() {
        setAddTitleText();
        imageView_image.setImage(FileImport.importImage(pictures.get(currentPictureIndex.get())));
        textField_pictureName.setText(pictures.get(currentPictureIndex.get()).getName());
        setupTagsTableView();
        setupCaptionsTableView();
    }

    private void startPictureAnalysis(Event buttonEvent) {
        visionAnalysisProcess = new VisionAnalysisProcess(pictures);
        spinner_status.progressProperty().bind(visionAnalysisProcess.progressProperty());

        Platform.runLater(() -> {
            anchorPane_content.setOpacity(0.75);
            spinner_status.setVisible(true);
        });

        final Thread analyzePicturesThread = new Thread(visionAnalysisProcess);
        analyzePicturesThread.setDaemon(true);
        analyzePicturesThread.start();

        visionAnalysisProcess.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                pictures = visionAnalysisProcess.getValue();
                saveAllPictures();

                Platform.runLater(() -> {
                    spinner_status.setVisible(false);
                });

                returnToPreviousScene();
            }
        });
    }

    private void saveAllPictures() {
        for (Picture picture : pictures) {
            pictureDAO.addPicture(picture);
        }
    }

    private void returnToPreviousScene() {
        SceneManager.returnToPreviousScene();
    }

    private void openRefreshedImageDetailsScene() {
        SceneManager.returnToPreviousScene();
        SceneManager.refreshCurrentScene();
    }
}