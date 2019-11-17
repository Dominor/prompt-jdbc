package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.common.Constants;
import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.utils.Security;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            String passwordHash = Security.getHash(password);
            String query = Constants.GET_CURRENT_USER_CREDENTIALS;
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet result = statement.executeQuery();
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

                PreparedStatement statement = dbConnection.prepareStatement(Constants.INSERT_USER_DATA);

                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getFirstName());
                statement.setString(5, user.getLastName());
                statement.setString(6, user.getPhone());

                int rowsAffected = statement.executeUpdate();
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
            String query = Constants.GET_USER_BY_USERNAME;
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
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
