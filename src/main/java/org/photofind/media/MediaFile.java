package org.photofind.media;

import java.io.File;

public class MediaFile {

    private File file;

    MediaFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return file.getName();
    }

    public String getAbsoluteFilePath() {
        return file.getAbsolutePath();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
