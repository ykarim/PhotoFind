package controller;

import media.Picture;

import java.util.List;

public interface PictureDAO {

    List<Picture> getAllPictures();

    Picture getPicture(Long id);

    Picture getPicture(String name);

    Picture addPicture(Picture picture);

    Picture updatePicture(Picture picture);

    Picture deletePicture(Picture picture);
}
