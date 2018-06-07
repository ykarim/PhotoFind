package org.photofind.media;

import org.photofind.media.descriptors.Description;
import org.photofind.media.descriptors.Tag;

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
    private Description description = new Description(new ArrayList<>(), new ArrayList<>());

    public Picture(File file) {
        super(file);

        //AutoGenerate
        this.id = UUID.randomUUID().toString();
        this.name = file.getName();
    }

    Picture(File file, String id, String name) {
        super(file);

        if (id == null || id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }

        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    Picture(File file, String id, String name, ArrayList<Tag> tags) {
        super(file);

        if (id == null || id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }

        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }

        if (tags == null) {
            tags = new ArrayList<>();
        } else {
            this.tags = tags;
        }
    }

    Picture(File file, String id, String name, ArrayList<Tag> tags, Description description) {
        super(file);

        if (id == null || id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }

        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }

        if (tags == null) {
            tags = new ArrayList<>();
        } else {
            this.tags = tags;
        }

        if (this.description == null) {
            this.description = new Description(new ArrayList<>(), new ArrayList<>());
        } else {
            this.description = description;
        }
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

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
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

    public void deleteDescription() {
        description = new Description(new ArrayList<>(), new ArrayList<>());
    }
}
