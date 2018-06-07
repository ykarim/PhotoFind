package org.photofind.ui.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.photofind.media.descriptors.Caption;

public class UICaption extends RecursiveTreeObject<UICaption> {

    private StringProperty textProperty;
    private DoubleProperty confidenceProperty;

    private Caption caption;

    public UICaption(Caption caption) {
        textProperty = new SimpleStringProperty(caption.getText());
        confidenceProperty = new SimpleDoubleProperty(caption.getConfidence());

        this.caption = caption;
    }

    public UICaption(String text) {
        textProperty = new SimpleStringProperty(text);
        confidenceProperty = new SimpleDoubleProperty(Double.NaN);

        caption = new Caption(text, Double.NaN);
    }

    public UICaption(String text, Double confidence) {
        textProperty = new SimpleStringProperty(text);
        confidenceProperty = new SimpleDoubleProperty(confidence);

        caption = new Caption(text, confidence);
    }

    public String getText() {
        return textProperty.get();
    }

    public void setText(String text) {
        textProperty.set(text);
        caption.setText(text);
    }

    public Double getConfidence() {
        return confidenceProperty.get();
    }

    public void setConfidence(Double confidence) {
        confidenceProperty.set(confidence);
        caption.setConfidence(confidence);
    }

    public Caption getCaption() {
        return caption;
    }
}
