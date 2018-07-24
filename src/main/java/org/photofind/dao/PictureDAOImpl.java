package org.photofind.dao;

import org.photofind.db.VisionDatabase;
import org.photofind.media.Picture;
import org.photofind.media.descriptors.Caption;
import org.photofind.media.descriptors.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PictureDAOImpl implements PictureDAO {

    private static List<Picture> pictures = new ArrayList<>();

    public PictureDAOImpl() {

    }

    @Override
    //static or nah
    public List<Picture> getAllPictures() {
        return pictures;
    }

    @Override
    public void setPictures(List<Picture> pictures) {
        PictureDAOImpl.pictures.addAll(pictures);
    }

    @Override
    public Picture getPictureById(String id) {
        Optional<Picture> matchingPicture = pictures.stream().
                filter(picture -> picture.getId().equals(id)).
                findFirst();

        return matchingPicture.orElse(null);
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

        VisionDatabase.insertIntoPictures(picture.getId(), picture.getName(), picture.getFile().getPath());

        for (Tag tag : picture.getTags()) {
            VisionDatabase.insertIntoTags(picture.getId(), tag.getName(), tag.getConfidence());
        }

        for (Caption caption : picture.getDescription().getCaptions()) {
            VisionDatabase.insertIntoCaptions(picture.getId(), caption.getText(), caption.getConfidence());
        }

        return picture;
    }

    //Change return type and use above getter implemented using stream below
    //set properties from updated pic
    @Override
    public Picture updatePicture(Picture updatedPicture) {
        Picture storedPicture = getPictureById(updatedPicture.getId());

        VisionDatabase.updatePictures(updatedPicture.getId(), updatedPicture.getName(), updatedPicture.getFile().getPath());

        //Delete all current tags and captions
        VisionDatabase.deletePictureTags(storedPicture.getId());
        VisionDatabase.deletePictureCaptions(storedPicture.getId());

        //Add current tags and captions anew as user may have modified tags or captions
        for (Tag tag : updatedPicture.getTags()) {
            VisionDatabase.insertIntoTags(updatedPicture.getId(), tag.getName(), tag.getConfidence());
        }

        for (Caption caption : updatedPicture.getDescription().getCaptions()) {
            VisionDatabase.insertIntoCaptions(updatedPicture.getId(), caption.getText(), caption.getConfidence());
        }

        //Replace stored picture with updated picture
        int storedPictureIndex = pictures.indexOf(storedPicture);
        pictures.set(storedPictureIndex, updatedPicture);
        return updatedPicture;
    }

    @Override
    public Picture removePicture(Picture pictureToRemove) {
        pictures.remove(pictureToRemove);

        VisionDatabase.deleteFromPictures(pictureToRemove.getId());
        VisionDatabase.deletePictureTags(pictureToRemove.getId());
        VisionDatabase.deletePictureCaptions(pictureToRemove.getId());

        return pictureToRemove;
    }

    public boolean removePicture(String id) {
        Optional<Picture> matchingPicture = pictures.stream().
                filter(picture -> picture.getId().equals(id)).
                findFirst();

        matchingPicture.ifPresent(this::removePicture);
        return matchingPicture.isPresent();
    }
}
