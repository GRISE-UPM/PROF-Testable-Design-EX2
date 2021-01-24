package es.upm.grise.profundizacion.HandleDocuments;

import java.sql.*;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;

public class MySQLHelper {
    // Connection to database (open during program execution)
    private final Connection connection;

    public MySQLHelper(ConfigProvider configProvider) throws NonRecoverableError {
        this.connection = getConnection(configProvider);
    }

    public int getLastDocumentId() throws NonRecoverableError {
        // Read from the COUNTERS table
        String query = "SELECT documentId FROM Counters";
        Statement statement = null;
        ResultSet resultSet = null;
        int documentId = 0;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(CANNOT_RUN_QUERY.getMessage());
            throw new NonRecoverableError();
        }

        // Get the last objectID
        int numberOfValues = 0;
        try {
            while (resultSet.next()) {
                documentId = resultSet.getInt("documentId");
                numberOfValues++;
            }
        } catch (SQLException e) {
            System.out.println(INCORRECT_COUNTER.getMessage());
            throw new NonRecoverableError();
        }

        // Only one objectID can be retrieved
        if (numberOfValues != 1) {
            System.out.println(CORRUPTED_COUNTER.getMessage());
            throw new NonRecoverableError();
        }

        // Close all DB connections
        try {
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(CONNECTION_LOST.getMessage());
            throw new NonRecoverableError();
        }

        return documentId;
    }

    public void updateLastDocumentId(int lastDocumentId) throws NonRecoverableError {
        // Access the COUNTERS table
        String query = "UPDATE Counters SET documentId = ?";
        int numUpdatedRows;

        // Update the documentID counter
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, lastDocumentId);
            numUpdatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println(CANNOT_UPDATE_COUNTER.getMessage());
            throw new NonRecoverableError();
        }

        // Check that the update has been effectively completed
        if (numUpdatedRows != 1) {
            System.out.println(CORRUPTED_COUNTER.getMessage());
            throw new NonRecoverableError();
        }
    }

    // Create the connection to the database
    private static Connection getConnection(ConfigProvider configProvider) throws NonRecoverableError {
        // Get the DB username and password
        String url = configProvider.getConfigValue("url");
        String username = configProvider.getConfigValue("username");
        String password = configProvider.getConfigValue("password");

        // Load DB driver
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());
            throw new NonRecoverableError();
        } catch (ClassNotFoundException e) {
            System.out.println(CANNOT_FIND_DRIVER.getMessage());
            throw new NonRecoverableError();
        }

        // Create DB connection
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(CANNOT_CONNECT_DATABASE.getMessage());
            throw new NonRecoverableError();

        }
    }
}
