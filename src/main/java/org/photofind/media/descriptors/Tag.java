package org.photofind.media.descriptors;

import java.io.Serializable;

public class Tag implements Serializable {

    private String name;
    private Double confidence;

    public Tag(String name) {
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }

        this.confidence = Double.NaN;
    }

    public Tag(String name, Double confidence) {
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }

        if (confidence == null) {
            this.confidence = Double.NaN;
        } else {
            this.confidence = confidence;
        }
    }

    public Tag(Tag tag) {
        this(tag.getName());
        this.confidence = new Double(confidence);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public int hashCode() {
        return name.hashCode() ^ confidence.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tag) {
            Tag pairo = (Tag) o;
            return this.name.equals(pairo.getName()) &&
                    this.confidence.equals(pairo.getConfidence());
        }

        return false;
    }
}