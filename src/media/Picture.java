package media;

import java.io.File;
import java.util.ArrayList;

public class Picture extends MediaFile {

    //Title of picture
    private String name;

    //Pic ID
    private final Long id;

    //List of tags associated with picture
    private ArrayList<String> tags = new ArrayList<>();

    Picture (File file, Long id, String name, ArrayList<String> tags) {
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }
}
