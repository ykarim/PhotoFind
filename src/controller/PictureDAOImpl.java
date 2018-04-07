package controller;

import media.Picture;

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
    public Picture getPicture(Long id) {
        for (Picture picture : pictures) {
            if (picture.getId().equals(id)) {
                return picture;
            }
        }
        return null;
    }

    @Override
    public Picture getPicture(String name) {
        for (Picture picture : pictures) {
            if (picture.getName().equals(name)) {
                return picture;
            }
        }
        return null;
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
