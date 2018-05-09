package media;

import java.io.File;
import java.util.ArrayList;

public class Picture extends MediaFile {

    //Pic ID
    private final Long id;
    //Title of picture
    private String name;
    //List of Tags associated with picture
    private ArrayList<Tag<String, Double>> tags = new ArrayList<>();

    Picture(File file, Long id, String name, ArrayList<Tag<String, Double>> tags) {
        super(file);
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Tag<String, Double>> getTags() {
        return tags;
    }

    public void addTag(String tagName, double confidence) {
        tags.add(new Tag<>(tagName, confidence));
    }

    public void addTag(Tag<String, Double> tag) {
        tags.add(tag);
    }

    public void removeTag(Tag<String, Double> tag) {
        tags.remove(tag);
    }

    public void removeTag(String tagName) {
        for (Tag<String, Double> tag : tags) {
            if (tag.getName().equals(tagName)) {
                tags.remove(tag);
            }
        }
    }
}
