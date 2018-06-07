package org.photofind.media.descriptors;

public class Caption {

    private String text;
    private Double confidence;

    public Caption(String text, Double confidence) {
        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }

        if (confidence == null) {
            this.confidence = Double.NaN;
        } else {
            this.confidence = confidence;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public int hashCode() {
        return text.hashCode() ^ confidence.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Caption) {
            Caption pairo = (Caption) o;
            return this.text.equals(pairo.getText()) &&
                    this.confidence.equals(pairo.getConfidence());
        }

        return false;
    }
}