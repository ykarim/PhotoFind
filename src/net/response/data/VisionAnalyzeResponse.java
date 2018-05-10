package net.response.data;

import media.descriptors.Description;
import media.descriptors.Tag;
import net.response.VisionResponse;
import org.apache.http.StatusLine;

import java.util.ArrayList;

public class VisionAnalyzeResponse implements VisionResponse {

    private StatusLine status;
    //try to change away from Object vague type //mb chg picdesc to capture all data from
    //List of Tags associated with picture
    private ArrayList<Tag> tags = new ArrayList<>();
    //Description of picture
    private Description description;

    public VisionAnalyzeResponse(StatusLine status) {
        this.status = status;
    }

    public VisionAnalyzeResponse(StatusLine status, ArrayList<Tag> tags, Description description) {
        this.status = status;
        this.tags = tags;
        this.description = description;
    }

    @Override
    public StatusLine getStatus() {
        return status;
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
