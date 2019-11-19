package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DocumentIdProvider {

	// Environment variable
	static String ENVIRON  = "APP_HOME";

	// ID for the newly created documents
	int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;
	
	// Variables necesarias para poder cambiar statement y resultSet
	Statement statement = null;
	ResultSet resultSet = null;

	// Singleton access
	static DocumentIdProvider instance;

	public static DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {

			instance = new DocumentIdProvider();
			return instance;

		}	
	}

	// Create the connection to the database
	public DocumentIdProvider() throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned
		String path = System.getenv(ENVIRON);

		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		} else {

			Properties propertiesInFile = new Properties();
			InputStream inputFile = null;

			// Load the property file
			loadPropertyFile(propertiesInFile, inputFile, path);

			// Get the DB username and password
			String url = propertiesInFile.getProperty("url");
			String username = propertiesInFile.getProperty("username");
			String password = propertiesInFile.getProperty("password");

			// Load DB driver
			loadDBDriver("com.mysql.jdbc.Driver");

			// Create DB connection
			createDBConnection(url, username, password);

			// Read from the COUNTERS table
			readFromCountersTable();

			// Get the last objectID
			int numberOfValues = getLastObjectID(resultSet, true);

			// Only one objectID can be retrieved
			if(numberOfValues != 1) {

				System.out.println(CORRUPTED_COUNTER.getMessage());          	
				throw new NonRecoverableError();

			}

			// Close all DB connections
			closeAllDBConnections(resultSet, statement);
		}
	}

	// Return the next valid objectID
	public int getDocumentId() throws NonRecoverableError {

		documentId++;

		// Access the COUNTERS table
		String query = "UPDATE Counters SET documentId = ?";
		int numUpdatedRows;

		// Update the documentID counter
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, documentId);
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

		return documentId;

	}
	
	public void loadPropertyFile(Properties properties, InputStream inputFile, String path) throws NonRecoverableError{
		// Load the property file
		try {
			inputFile = new FileInputStream(path + "config.properties");
			properties.load(inputFile);

		} catch (FileNotFoundException e) {

			System.out.println(NON_EXISTING_FILE.getMessage());          	
			throw new NonRecoverableError();

		} catch (IOException e) {

			System.out.println(CANNOT_READ_FILE.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	public void loadDBDriver(String driver) throws NonRecoverableError
	{
		try {

			Class.forName(driver).newInstance();

		} catch (InstantiationException e) {

			System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
			throw new NonRecoverableError();

		} catch (IllegalAccessException e) {

			System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
			throw new NonRecoverableError();

		} catch (ClassNotFoundException e) {

			System.out.println(CANNOT_FIND_DRIVER.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	public void createDBConnection(String url, String username, String password) throws NonRecoverableError{
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();
		}
	}
	
	public void readFromCountersTable() throws NonRecoverableError{

		String query = "SELECT documentId FROM Counters";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError();
		}
	}
	
	public int getLastObjectID(ResultSet resultSet, Boolean real) throws NonRecoverableError {
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
		return numberOfValues;
	}
	
	public void closeAllDBConnections(ResultSet resultSet, Statement statement) throws NonRecoverableError {
		try {
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println(CONNECTION_LOST.getMessage());          	
			throw new NonRecoverableError();
		}
	}
}
