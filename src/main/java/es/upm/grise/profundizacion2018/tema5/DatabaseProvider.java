package es.upm.grise.profundizacion2018.tema5;

import java.sql.*;

import static es.upm.grise.profundizacion2018.tema5.Error.*;

public class DatabaseProvider {

    private Connection connection;
    private Statement statement;
    private PreparedStatement updateStatement;
    private ResultSet resultSet;



    private void loadDriver() throws NonRecoverableError {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage());
        } catch (IllegalAccessException e) {
            throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage());
        } catch (ClassNotFoundException e) {
            throw new NonRecoverableError(CANNOT_FIND_DRIVER.getMessage());
        }
    }

    private void conectToDatabase(String url, String username, String password) throws NonRecoverableError {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new NonRecoverableError(CANNOT_CONNECT_DATABASE.getMessage());
        }
    }

    public void openConnection(String url, String username, String password) throws NonRecoverableError {
        loadDriver();
        conectToDatabase(url, username, password);
    }

    public ResultSet executeQuery(String query) throws NonRecoverableError {
        try {
            this.statement = this.connection.createStatement();
            this.resultSet = statement.executeQuery(query);
            return this.resultSet;
        } catch (SQLException e) {
            throw new NonRecoverableError(CANNOT_RUN_QUERY.getMessage());
        }
    }

    public  int executeUpdateQuery(String query, int param) throws NonRecoverableError {
        try {
            this.updateStatement = connection.prepareStatement(query);
            this.updateStatement.setInt(1, param);
            return this.updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new NonRecoverableError(CANNOT_UPDATE_COUNTER.getMessage());
        }
    }

    public void closeStatement() throws NonRecoverableError {
        try {
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new NonRecoverableError(CONNECTION_LOST.getMessage());
        }
    }

    public void closeUpdateStatement() throws NonRecoverableError {
        try {
            resultSet.close();
            updateStatement.close();
        } catch (SQLException e) {
            throw new NonRecoverableError(CONNECTION_LOST.getMessage());
        }
    }
}
