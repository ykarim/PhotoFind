package media.descriptors;

import java.util.ArrayList;

public class Description {

    private ArrayList<Caption> captions = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();

    public Description(ArrayList<Caption> captions, ArrayList<Tag> tags) {
        this.captions = captions;
        this.tags = tags;
    }

    public ArrayList<Caption> getCaptions() {
        return captions;
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
