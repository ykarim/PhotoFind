package org.photofind.media.descriptors;

import java.io.Serializable;
import java.util.ArrayList;

public class Description implements Serializable {

    private ArrayList<Caption> captions;
    private ArrayList<Tag> tags;

    public Description(ArrayList<Caption> captions, ArrayList<Tag> tags) {
        //Ensure both variables are initialized and not null
        if (captions != null) {
            this.captions = captions;
        } else {
            this.captions = new ArrayList<>();
        }

        if (tags != null) {
            this.tags = tags;
        } else {
            this.tags = new ArrayList<>();
        }
    }

    public Description(Description description) {
        ArrayList<Caption> newCaptions = new ArrayList<>();
        description.getCaptions().forEach(caption -> newCaptions.add(new Caption(caption)));
        this.captions = newCaptions;

        ArrayList<Tag> newTags = new ArrayList<>();
        description.getTags().forEach(tag -> newTags.add(new Tag(tag)));
        this.tags = newTags;
    }

    public ArrayList<Caption> getCaptions() {
        return captions;
    }

    public void setCaptions(ArrayList<Caption> captions) {
        this.captions = captions;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void addCaption(Caption caption) {
        captions.add(caption);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void deleteCaption(Caption caption) {
        captions.remove(caption);
    }

    public void deleteTag(Tag tag) {
        tags.remove(tag);
    }
}
