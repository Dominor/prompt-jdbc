package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.common.Constants;
import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.utils.Security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JDBCUserService implements UserService{

    @Override
    public boolean authenticate(String username, String password) {
        User user = findByName(username);

        if (user == null) {
            return false;
        }
        //return user.getPassword().equals(Security.getHash(password));
        return user.getPassword().equals(password);
    }

    @Override
    public void add(User user) {

        if (user == null) {
            return;
        }

        ConnectionManager connectionManager = new ConnectionManager();
        Connection dbConnection = connectionManager.getConnection();
        if (findByName(user.getUsername()) != null) {
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
            } finally {
                connectionManager.close();
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
        ConnectionManager connectionManager = new ConnectionManager();
        Connection dbConnection = connectionManager.getConnection();
        try {
            Statement statement = dbConnection.createStatement();

            ResultSet resultSet = statement.executeQuery(Constants.GET_USER_BY_USERNAME + username + ";");
            if (resultSet.next()) {
                usernameValue = resultSet.getString("username");
                passworldValue = resultSet.getString("password");
                emailValue = resultSet.getString("email");
                firstNameValue = resultSet.getString("email");
                lastNameValue = resultSet.getString("lastname");
                phoneValue = resultSet.getString("phone");

                user = new User(usernameValue, emailValue, passworldValue, firstNameValue, lastNameValue, phoneValue);
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Creating SQL statement: " + e.getMessage());
        } finally {
            connectionManager.close();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }
}
