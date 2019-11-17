package org.academiadecodigo.bootcamp.common;

public class Constants {

    public static final String CONNECTION_FAILED = "Connection failed!";
    public static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306";
    public static final String DEFAULT_DB_USERNAME = "dominor";
    public static final String DEFAULT_DB__PASSWORD = "sesamo";
    public static final String GET_USER_BY_USERNAME = "SELECT username, password, email, firstname, lastname, phone FROM user WHERE username = ?;";
    public static final String INSERT_USER_DATA = "INSERT INTO user(username, password, email, firstname, lastname, phone) VALUES( ?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String GET_TOTAL_USERS = "SELECT COUNT(id) AS TotalUsers FROM user;";
    public static final String GET_CURRENT_USER_CREDENTIALS = "SELECT username, password FROM user WHERE username = ? AND password = ?;";
    public static final String SELECT_USER_DATABASE = "USE ac";
}
