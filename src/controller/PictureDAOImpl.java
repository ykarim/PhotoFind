package controller;

import media.Picture;
import media.descriptors.Tag;

import java.util.ArrayList;
import java.util.List;

public class PictureDAOImpl implements PictureDAO {

    List<Picture> pictures;

    public PictureDAOImpl() {
        pictures = new ArrayList<Picture>();
    }

    @Override
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
            if (picture.getName().equals(name)) {
                results.add(picture);
            }
        }

        return results;
    }

    public ArrayList<Picture> getPicturesByTagName(String tagName) {
        ArrayList<Picture> results = new ArrayList<>();

        for (Picture picture : pictures) {
            for (Tag tag : picture.getTags()) {
                if (tag.getName().equals(tagName)) {
                    results.add(picture);

                    //Break out of current inner loop to not repeat picture in results
                    break;
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
    public Picture deletePicture(Picture pictureToDelete) {
        pictures.remove(pictureToDelete);
        return pictureToDelete;
    }
}
