package org.photofind.controller;

import org.photofind.media.Picture;

import java.util.List;

public interface PictureDAO {

    List<Picture> getAllPictures();

    Picture getPictureById(String id);

    Picture getPictureByName(String name);

    Picture addPicture(Picture picture);

    Picture updatePicture(Picture picture);

    Picture removePicture(Picture picture);
}
