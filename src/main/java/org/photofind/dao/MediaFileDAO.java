package org.photofind.dao;

import org.photofind.media.MediaFile;

import java.util.List;

public interface MediaFileDAO {

    List<MediaFile> getAllMediaFiles();

    MediaFile getMediaFile(String name);

    MediaFile addMediaFile(MediaFile mediaFile);

    MediaFile updateMediaFile(MediaFile mediaFile);

    MediaFile deleteMediaFile(MediaFile mediaFile);
}
