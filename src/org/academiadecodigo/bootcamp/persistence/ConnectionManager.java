package org.academiadecodigo.bootcamp.persistence;


import org.academiadecodigo.bootcamp.common.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private Connection connection = null;

    public Connection getConnection() {

        try {
            if (connection == null) {
                connection = DriverManager.getConnection(Constants.DEFAULT_DB_URL, Constants.DEFAULT_DB_USERNAME, Constants.DEFAULT_DB__PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Failure to connecto to database: "  + e.getMessage());
        }
        return connection;
    }

    public void close() {

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failure to close database connection: " + e.getMessage());
        }
    }
}
