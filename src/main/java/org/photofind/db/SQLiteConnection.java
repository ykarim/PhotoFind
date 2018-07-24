package org.photofind.db;

import java.sql.*;

public class SQLiteConnection {

    private static Connection connection = null;
    private static boolean connected;

    void connect() {
        String url = "jdbc:sqlite:" + System.getProperty("user.home") + "\\PhotoFind\\photofind.db";

        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                connected = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connected = true;
    }

    boolean isConnected() {
        return connected;
    }

    void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Statement createStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    PreparedStatement createPreparedStatement(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
                connected = false;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
