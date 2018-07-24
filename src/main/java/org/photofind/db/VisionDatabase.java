package org.photofind.db;

import org.photofind.dao.PictureDAOImpl;
import org.photofind.media.Picture;
import org.photofind.media.descriptors.Caption;
import org.photofind.media.descriptors.Description;
import org.photofind.media.descriptors.Tag;
import org.photofind.util.FileImport;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VisionDatabase {

    private static SQLiteConnection connection = new SQLiteConnection();

    public static void start() {
        File appDirectory = new File(System.getProperty("user.home") + "\\PhotoFind");
        File appDatabase = new File(appDirectory.toString() + "\\photofind.db");

        if (!appDirectory.exists() && !appDatabase.exists()) { //Known issue if db file deleted but folder exists errors
            //Database doesn't exist so create PhotoFind folder and database
            boolean createDirectorySuccess = appDirectory.mkdirs();
            connection.connect();
            if (createDirectorySuccess) {
                createTables();
            }
        } else {
            connection.connect();
            loadData();
        }
    }

    public static void loadData() {
        ResultSet dbPictures = selectFromPictures();
        ArrayList<Picture> dbPicturesList = new ArrayList<>();

        try {
            while (dbPictures != null && dbPictures.next()) {
                File currentPictureFile = FileImport.importFile(dbPictures.getString("File Path"));
                String currentPictureId = dbPictures.getString("Picture ID");

                ResultSet currentPictureTags = selectFromTags(currentPictureId);
                ArrayList<Tag> currentPictureTagsList = new ArrayList<>();
                while (currentPictureTags != null && currentPictureTags.next()) {
                    currentPictureTagsList.add(new Tag(currentPictureTags.getString("Tag Text"),
                            currentPictureTags.getDouble("Tag Confidence")));
                }

                ResultSet currentPictureCaptions = selectFromCaptions(currentPictureId);
                ArrayList<Caption> currentPictureCaptionsList = new ArrayList<>();
                while (currentPictureCaptions != null && currentPictureCaptions.next()) {
                    currentPictureCaptionsList.add(
                            new Caption(currentPictureCaptions.getString("Caption Text"),
                                    currentPictureCaptions.getDouble("Caption Confidence")));
                }
                Description currentPictureDesc = new Description(currentPictureCaptionsList, null);

                dbPicturesList.add(new Picture(currentPictureFile,
                        currentPictureId,
                        dbPictures.getString("Name"),
                        currentPictureTagsList,
                        currentPictureDesc));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Should I create instance of DAO here or elsewhere?
        PictureDAOImpl pictureDAO = new PictureDAOImpl();
        pictureDAO.setPictures(dbPicturesList);
    }

    private static void createTables() {
        String createPictureTableQuery =
                "CREATE TABLE IF NOT EXISTS Pictures (" +
                        "[Picture ID] TEXT CONSTRAINT [Picture ID] PRIMARY KEY," +
                        "Name         TEXT," +
                        "[File Path]  TEXT" +
                        ");";

        String createTagsTableQuery =
                "CREATE TABLE IF NOT EXISTS Tags (" +
                        "[Tag ID]         INTEGER   CONSTRAINT [Tag ID] PRIMARY KEY AUTOINCREMENT," +
                        "[Picture ID]     TEXT   CONSTRAINT [Picture ID] REFERENCES Pictures ([Picture ID])," +
                        "[Tag Text]       TEXT," +
                        "[Tag Confidence] DOUBLE" +
                        ");";

        String createCaptionsTableQuery =
                "CREATE TABLE IF NOT EXISTS Captions (" +
                        "[Caption ID]         INTEGER   CONSTRAINT [Caption ID] PRIMARY KEY AUTOINCREMENT," +
                        "[Picture ID]         TEXT   CONSTRAINT [Picture ID] REFERENCES Pictures ([Picture ID])," +
                        "[Caption Text]       TEXT," +
                        "[Caption Confidence] DOUBLE" +
                        ");";

        try {
            connection.createStatement().execute(createPictureTableQuery);
            connection.createStatement().execute(createTagsTableQuery);
            connection.createStatement().execute(createCaptionsTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertIntoPictures(String id, String name, String filePath) {
        try {
            String addPictureQuery = "INSERT INTO Pictures " +
                    "VALUES (?, ?, ?);";

            connection.setAutoCommit(false);
            PreparedStatement addPictureStatement = connection.createPreparedStatement(addPictureQuery);
            addPictureStatement.setString(1, id);
            addPictureStatement.setString(2, name);
            addPictureStatement.setString(3, filePath);
            addPictureStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoTags(String pictureId, String tagName, Double tagConfidence) {
        try {
            String addPictureQuery = "INSERT INTO Tags ('Picture ID', 'Tag Text', 'Tag Confidence')" +
                    "VALUES (?, ?, ?);";

            connection.setAutoCommit(false);
            PreparedStatement addTagStatement = connection.createPreparedStatement(addPictureQuery);
            addTagStatement.setString(1, pictureId);
            addTagStatement.setString(2, tagName);
            addTagStatement.setDouble(3, tagConfidence);
            addTagStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoCaptions(String pictureId, String captionText, Double captionConfidence) {
        try {
            String addPictureQuery = "INSERT INTO Captions ('Picture ID', 'Caption Text', 'Caption Confidence')" +
                    "VALUES (?, ?, ?);";

            connection.setAutoCommit(false);
            PreparedStatement addCaptionStatement = connection.createPreparedStatement(addPictureQuery);
            addCaptionStatement.setString(1, pictureId);
            addCaptionStatement.setString(2, captionText);
            addCaptionStatement.setDouble(3, captionConfidence);
            addCaptionStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePictures(String id, String newName, String newFilePath) {
        try {
            String updatePictureQuery = "UPDATE Pictures " +
                    "SET \"Name\" = ?, \"File Path\" = ? " +
                    "WHERE \"Picture ID\" = ?;";

            connection.setAutoCommit(false);
            PreparedStatement updatePictureStatement = connection.createPreparedStatement(updatePictureQuery);
            updatePictureStatement.setString(1, newName);
            updatePictureStatement.setString(2, newFilePath);
            updatePictureStatement.setString(3, id);
            updatePictureStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromPictures(String pictureID) {
        try {
            String deletePictureQuery = "DELETE FROM Pictures " +
                    "WHERE \"Picture ID\" = ?;";

            connection.setAutoCommit(false);
            PreparedStatement deletePictureStatement = connection.createPreparedStatement(deletePictureQuery);
            deletePictureStatement.setString(1, pictureID);
            deletePictureStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePictureTags(String pictureID) {
        try {
            String deleteTagQuery = "DELETE FROM Tags " +
                    "WHERE \"Picture ID\" = ?;";

            connection.setAutoCommit(false);
            PreparedStatement deleteTagStatement = connection.createPreparedStatement(deleteTagQuery);
            deleteTagStatement.setString(1, pictureID);
            deleteTagStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePictureCaptions(String pictureID) {
        try {
            String deleteCaptionQuery = "DELETE FROM Captions " +
                    "WHERE \"Picture ID\" = ?;";

            connection.setAutoCommit(false);
            PreparedStatement deleteTagStatement = connection.createPreparedStatement(deleteCaptionQuery);
            deleteTagStatement.setString(1, pictureID);
            deleteTagStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet selectFromPictures() {
        try {
            String selectPictureQuery = "SELECT * FROM Pictures;";

            connection.setAutoCommit(false);
            PreparedStatement selectPictureStatement = connection.createPreparedStatement(selectPictureQuery);
            ResultSet pictureResults = selectPictureStatement.executeQuery();
            connection.commit();
            connection.setAutoCommit(true);
            return pictureResults;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet selectAllFromTags() {
        try {
            String selectTagQuery = "SELECT * FROM Tags;";

            connection.setAutoCommit(false);
            PreparedStatement selectTagStatement = connection.createPreparedStatement(selectTagQuery);
            ResultSet tagResults = selectTagStatement.executeQuery();
            connection.commit();
            connection.setAutoCommit(true);
            return tagResults;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet selectFromTags(String pictureId) {
        try {
            String selectTagQuery = "SELECT * FROM Tags " +
                    "WHERE \"Picture ID\" = ?;";

            connection.setAutoCommit(false);
            PreparedStatement selectTagStatement = connection.createPreparedStatement(selectTagQuery);
            selectTagStatement.setString(1, pictureId);
            ResultSet tagResults = selectTagStatement.executeQuery();
            connection.commit();
            connection.setAutoCommit(true);
            return tagResults;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet selectAllFromCaptions() {
        try {
            String selectCaptionQuery = "SELECT * FROM Captions;";

            connection.setAutoCommit(false);
            PreparedStatement selectCaptionStatement = connection.createPreparedStatement(selectCaptionQuery);
            ResultSet captionResults = selectCaptionStatement.executeQuery();
            connection.commit();
            connection.setAutoCommit(true);
            return captionResults;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet selectFromCaptions(String pictureId) {
        try {
            String selectCaptionQuery = "SELECT * FROM Captions " +
                    "WHERE \"Picture ID\" = ?;";

            connection.setAutoCommit(false);
            PreparedStatement selectCaptionStatement = connection.createPreparedStatement(selectCaptionQuery);
            selectCaptionStatement.setString(1, pictureId);
            ResultSet captionResults = selectCaptionStatement.executeQuery();
            connection.commit();
            connection.setAutoCommit(true);
            return captionResults;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isConnected() {
        return connection.isConnected();
    }
}
