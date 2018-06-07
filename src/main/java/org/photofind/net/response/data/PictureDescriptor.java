package org.photofind.net.response.data;

import org.photofind.media.descriptors.Description;
import org.photofind.media.descriptors.Tag;

import java.util.ArrayList;

@Deprecated
//Unused as it would lead to overabundance of files to represent diff types of responses
public class PictureDescriptor {

    //List of Tags associated with picture
    private ArrayList<Tag> tags;
    //Description of picture
    private Description description;

    public PictureDescriptor(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public PictureDescriptor(ArrayList<Tag> tags, Description description) {
        this.tags = tags;
        this.description = description;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public Description getDescription() {
        return description;
    }

    public void addTag(String tagName, double confidence) {
        tags.add(new Tag(tagName, confidence));
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public void removeTag(String tagName) {
        for (Tag tag : tags) {
            if (tag.getName().equals(tagName)) {
                tags.remove(tag);
            }
        }
    }

    public void addTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public void addDescription(Description description) {
        this.description = description;
    }

    public void deleteDescription() {
        description = new Description(new ArrayList<>(), new ArrayList<>());
    }
}
