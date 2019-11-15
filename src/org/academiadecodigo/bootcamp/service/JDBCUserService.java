package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.common.Constants;
import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.utils.Security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 * - Protect against SQL Injection using prepared statements, see slides. String.Format() can be used as well to insert
 * unknown values in the middle of strings without doing complicated string concatenations.
 */

public class JDBCUserService implements UserService {

    private ConnectionManager connectionManager;
    private Connection dbConnection;

    public JDBCUserService(ConnectionManager connectionManager) {

        this.connectionManager = connectionManager;
        dbConnection = connectionManager.getConnection();
        selectDatabase();
    }

    private void selectDatabase() {

        try {
            Statement statement = dbConnection.createStatement();
            statement.executeQuery(Constants.SELECT_USER_DATABASE);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean authenticate(String username, String password) {

        try {
            Statement statement = dbConnection.createStatement();
            String passwordHash = Security.getHash(password);
            String query = Constants.GET_CURRENT_USER_CREDENTIALS + "username = \"" + username + "\" AND password = \"" + passwordHash + "\";";
            ResultSet result = statement.executeQuery(query);
            if (!result.next()) {
                return false;
            }
            if (username.equals(result.getString("username")) && result.getString("password").equals(passwordHash)) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void add(User user) {

        if (user == null) {
            return;
        }

        if (findByName(user.getUsername()) == null) {
            try {
                Statement statement = dbConnection.createStatement();

                String updateQuery = new StringBuilder(Constants.INSERT_USER_DATA)
                        .append("\"")
                        .append(user.getUsername())
                        .append("\", \"")
                        .append(user.getPassword())
                        .append("\", \"")
                        .append(user.getEmail())
                        .append("\", \"")
                        .append(user.getFirstName())
                        .append("\", \"")
                        .append(user.getLastName())
                        .append("\", \"")
                        .append(user.getPhone())
                        .append("\");").toString();
                int rowsAffected = statement.executeUpdate(updateQuery);
                if (rowsAffected != 1) {
                    throw new SQLException("ERROR: Inserting new user record.");
                }

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public User findByName(String username) {
        String usernameValue = null;
        String passworldValue = null;
        String emailValue = null;
        String firstNameValue = null;
        String lastNameValue = null;
        String phoneValue = null;
        User user = null;

        try {
            Statement statement = dbConnection.createStatement();

            String query = Constants.GET_USER_BY_USERNAME + "'" + username  + "';";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                usernameValue = resultSet.getString("username");
                passworldValue = resultSet.getString("password");
                emailValue = resultSet.getString("email");
                firstNameValue = resultSet.getString("firstname");
                lastNameValue = resultSet.getString("lastname");
                phoneValue = resultSet.getString("phone");

                user = new User(usernameValue, emailValue, passworldValue, firstNameValue, lastNameValue, phoneValue);
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Creating SQL statement: " + e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();

        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet;
            boolean result = statement.execute(Constants.GET_ALL_USERS);
            if (result) {
                resultSet = statement.getResultSet();
                resultSet.next();
                do {
                    allUsers.add(new User(resultSet.getString("username"), resultSet.getString("email"),
                            resultSet.getString("password"), resultSet.getString("firstname"),
                            resultSet.getString("lastname"), resultSet.getString("phone")));
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return allUsers;
    }

    @Override
    public int count() {
        int count = 0;

        try {
            Statement statement = dbConnection.createStatement();
            count = statement.executeQuery(Constants.GET_TOTAL_USERS).getInt("TotalUsers");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return count;
    }
}
