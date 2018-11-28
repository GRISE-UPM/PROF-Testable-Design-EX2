package es.upm.grise.profundizacion2018.tema5;

import static es.upm.grise.profundizacion2018.tema5.Error.*;

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
	private static String ENVIRON = "APP_HOME";

	// ID for the newly created documents
	private int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

	// Singleton access
	private static DocumentIdProvider instance;

	public static DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)
			
			return instance;

		else {
			instance = new DocumentIdProvider();
			return instance;

		}	
	}

	public static Properties checkfile(String file) throws NonRecoverableError{
		
		String path = System.getenv(ENVIRON);
		if (path == null) {
			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();
		}
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;
			// Load the property file
		try {
			inputFile = new FileInputStream(path + file);
			propertiesInFile.load(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println(NON_EXISTING_FILE.getMessage());          	
			throw new NonRecoverableError(NON_EXISTING_FILE.getMessage(), e);
		} catch (IOException e) {
			System.out.println(CANNOT_READ_FILE.getMessage());          	
			throw new NonRecoverableError(CANNOT_READ_FILE.getMessage(), e);
		}
		return propertiesInFile;
	}
	// Create the connection to the database
	
	public static void checkDriver(String driver) throws NonRecoverableError{
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
			throw new NonRecoverableError(CANNOT_FIND_DRIVER.getMessage(), e);
		}
	}
	
	DocumentIdProvider() throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned
		Properties propertiesInFile = checkfile("config.properties");

			// Get the DB username and password
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");
			// Load DB driver
		checkDriver("com.mysql.jdbc.Driver");
		// Create DB connection
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();
		}

			// Read from the COUNTERS table
		String query = "SELECT documentId FROM Counters";
		Statement statement = null;
		ResultSet resultSet = null;
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
		if(numberOfValues != 1) {
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
	
	
}
