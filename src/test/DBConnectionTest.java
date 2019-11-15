package test;


import org.academiadecodigo.bootcamp.persistence.ConnectionManager;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class DBConnectionTest {

    @Test
    public void ConnectionManagerShouldReturnConnectionIfSuccessful() {

        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();

        Assert.assertNotNull("Connection failed!", connection);
        connectionManager.close();
    }
}
