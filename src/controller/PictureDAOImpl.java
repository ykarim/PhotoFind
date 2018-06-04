package controller;

import media.Picture;
import media.descriptors.Tag;

import java.util.ArrayList;
import java.util.List;

public class PictureDAOImpl implements PictureDAO {

    static List<Picture> pictures = new ArrayList<>();

    @Override
    //static or nah
    public List<Picture> getAllPictures() {
        return pictures;
    }

    @Override
    public Picture getPictureById(String id) {
        for (Picture picture : pictures) {
            if (picture.getId().equals(id)) {
                return picture;
            }
        }
        return null;
    }

    @Override
    public Picture getPictureByName(String name) {
        for (Picture picture : pictures) {
            if (picture.getName().equals(name)) {
                return picture;
            }
        }
        return null;
    }

    public ArrayList<Picture> getPicturesByName(String name) {
        ArrayList<Picture> results = new ArrayList<>();

        for (Picture picture : pictures) {
            if (picture.getName().equalsIgnoreCase(name)) {
                results.add(picture);
            }
        }

        return results;
    }

    public ArrayList<Picture> getPicturesByTagName(String tagName) {
        ArrayList<Picture> results = new ArrayList<>();

        for (Picture picture : pictures) {
            inner:
            for (Tag tag : picture.getTags()) {
                if (tag.getName().equalsIgnoreCase(tagName)) {
                    results.add(picture);

                    //Break out of current inner loop to not repeat picture in results
                    break inner;
                }
            }
        }

        return results;
    }

    @Override
    public Picture addPicture(Picture picture) {
        pictures.add(picture);
        return picture;
    }

    @Override
    public Picture updatePicture(Picture updatedPicture) {
        for (Picture picture : pictures) {
            if (picture.getId().equals(updatedPicture.getId())) {
                picture = updatedPicture;
            }
        }
        return updatedPicture;
    }

    @Override
    public Picture removePicture(Picture pictureToRemove) {
        pictures.remove(pictureToRemove);
        return pictureToRemove;
    }

    public boolean removePicture(String id) {
        return pictures.removeIf(picture -> picture.getId().equals(id));
    }
}
