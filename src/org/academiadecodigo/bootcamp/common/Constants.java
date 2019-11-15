package org.academiadecodigo.bootcamp.common;

public class Constants {

    public static final String CONNECTION_FAILED = "Connection failed!";
    public static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306";
    public static final String DEFAULT_DB_USERNAME = "root";
    public static final String DEFAULT_DB__PASSWORD = "";
    public static final String GET_USER_BY_USERNAME = "SELECT username, password, email, firstname, lastname, phone FROM user WHERE username = ";
    public static final String INSERT_USER_DATA = "INSERT INTO user(username, password, email, firstname, lastname, phone) VALUES( ";
}
