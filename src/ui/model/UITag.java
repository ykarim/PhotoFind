package ui.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import media.descriptors.Tag;

public class UITag extends RecursiveTreeObject<UITag> {

    private StringProperty nameProperty;
    private DoubleProperty confidenceProperty;

    private Tag tag;

    public UITag(Tag tag) {
        nameProperty = new SimpleStringProperty(tag.getName());
        confidenceProperty = new SimpleDoubleProperty(tag.getConfidence());

        this.tag = tag;
    }

    public UITag(String name) {
        nameProperty = new SimpleStringProperty(name);
        confidenceProperty = new SimpleDoubleProperty(Double.NaN);

        tag = new Tag(name);
    }

    public UITag(String name, Double confidence) {
        nameProperty = new SimpleStringProperty(name);
        confidenceProperty = new SimpleDoubleProperty(confidence);

        tag = new Tag(name, confidence);
    }

    public String getName() {
        return nameProperty.get();
    }

    public void setName(String name) {
        nameProperty.set(name);
        tag.setName(name);
    }

    public Double getConfidence() {
        return confidenceProperty.get();
    }

    public void setConfidence(Double confidence) {
        confidenceProperty.set(confidence);
        tag.setConfidence(confidence);
    }

    public Tag getTag() {
        return tag;
    }
}
