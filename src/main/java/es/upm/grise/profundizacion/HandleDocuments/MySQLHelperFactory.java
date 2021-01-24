package es.upm.grise.profundizacion.HandleDocuments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;

public class MySQLHelperFactory {
    public MySQLHelper createMySQLHelper(ConfigProvider configProvider, ReflectionWrapper reflectionWrapper)
            throws NonRecoverableError {
        loadMySQLDriver(reflectionWrapper);
        return new MySQLHelper(getConnection(configProvider));
    }

    protected void loadMySQLDriver(ReflectionWrapper reflectionWrapper) throws NonRecoverableError {
        try {
            reflectionWrapper.findClass("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());
            throw new NonRecoverableError();
        } catch (ClassNotFoundException e) {
            System.out.println(CANNOT_FIND_DRIVER.getMessage());
            throw new NonRecoverableError();
        }
    }

    // Create the connection to the database
    protected Connection getConnection(ConfigProvider configProvider) throws NonRecoverableError {
        // Get the DB username and password
        String url = configProvider.getConfigValue("url");
        String username = configProvider.getConfigValue("username");
        String password = configProvider.getConfigValue("password");

        // Create DB connection
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(CANNOT_CONNECT_DATABASE.getMessage());
            throw new NonRecoverableError();
        }
    }
}
