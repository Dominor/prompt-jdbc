package org.academiadecodigo.bootcamp.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String dbURL = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "";

    private Connection connection = null;

    public Connection getConnection() {

        try {
            if (connection == null) {
                connection = DriverManager.getConnection(dbURL, user, password);
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
