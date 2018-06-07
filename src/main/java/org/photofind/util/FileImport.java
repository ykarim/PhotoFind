package org.photofind.util;

import javafx.scene.image.Image;
import org.photofind.media.Picture;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class FileImport {

    /**
     * Converts file's current path to formatted, decoded path
     *
     * @param file to be formatted
     * @return file containing newly formatted, decoded path
     */
    private static File formatFile(File file) {
        return new File(decodePath(file.getPath()));
    }

    /**
     * Decodes path using UTF-8 scheme to remove symbols such as "%20"
     *
     * @param path path to be decoded
     * @return decoded path
     */
    private static String decodePath(String path) {
        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return path;
        }
    }

    /**
     * Formats file path to remove any encoding such as "%20" and returns newly created file
     *
     * @param path path of file
     * @return file made using specific path
     */
    public static File importFile(String path) {
        //Get File at path
        File file = new File(path);

        //Decode path to remove %20 which throws off File constructor
        String formattedPath = decodePath(path);

        //Return newly created File made using formatted path
        return new File(formattedPath);
    }

    /**
     * Imports file relative to class file using {@code getClass().getResource}
     *
     * @param path path of file relative to FileImport Class
     * @return file made using formatted path
     */
    public static File importLocalFile(String path) {
        ClassLoader classLoader = (new FileImport()).getClass().getClassLoader();

        return importFile(classLoader.getResource(path).getPath());
    }

    /**
     * Returns URL of file
     * Used in FXMLLoader to retrieve location of FXML files
     *
     * @param file
     * @return URL of file
     */
    public static URL getFileURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Decode path and obtain image
     *
     * @param path non-relative, encoded path to be decoded
     * @return
     */
    private static Image importImage(String path) {
        //Decode path
        String imagePath = decodePath(path);

        //Create image using path with "file:" prefix
        return new Image("file:" + imagePath);
    }

    /**
     * Decodes file path and returns image
     *
     * @param file image
     * @return Image
     */
    public static Image importImage(File file) {
        return importImage(file.getPath());
    }

    /**
     * Extracts file from picture to decode file path and return image
     *
     * @param picture picture object with file
     * @return Image
     */
    public static Image importImage(Picture picture) {
        return importImage(picture.getFile());
    }
}
