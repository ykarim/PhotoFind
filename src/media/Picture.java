package media;

import media.descriptors.Description;
import media.descriptors.Tag;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Picture extends MediaFile {

    //Pic ID
    private final String id;
    //Title of picture
    private String name;
    //List of Tags associated with picture
    private ArrayList<Tag> tags = new ArrayList<>();
    //Description of picture
    private Description description;

    Picture(File file) {
        super(file);

        //AutoGenerate
        this.id = UUID.randomUUID().toString();
        this.name = file.getName();
    }

    Picture(File file, String id, String name) {
        super(file);
        this.id = id;
        this.name = name;
    }

    Picture(File file, String id, String name, ArrayList<Tag> tags) {
        super(file);
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    Picture(File file, String id, String name, ArrayList<Tag> tags, Description description) {
        super(file);
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
