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
	static String ENVIRON  = "APP_HOME";

	// ID for the newly created documents
	int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

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
	
	Properties getConfig(String path) throws NonRecoverableError {
	
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;

		// Load the property file
		try {
			inputFile = new FileInputStream(path + "config.properties");
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
	
	Connection getConnection(Properties propertiesInFile, String driver) throws NonRecoverableError {
		// Get the DB username and password
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");

		// Load DB driver
		try {

			Class.forName(driver).newInstance();

		} catch (InstantiationException e) {

			System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
			throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage(), e);

		} catch (IllegalAccessException e) {

			System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
			throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage(), e);

		} catch (ClassNotFoundException e) {

			System.out.println(CANNOT_FIND_DRIVER.getMessage());          	
			throw new NonRecoverableError(CANNOT_FIND_DRIVER.getMessage(), e);

		}

		// Create DB connection
		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {

			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError(CANNOT_CONNECT_DATABASE.getMessage(), e);

		}
		
		return connection;
	}
	
	int getCurrentId() throws NonRecoverableError {
		// Read from the COUNTERS table
		String query = "SELECT documentId FROM Counters";
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {

			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError(CANNOT_RUN_QUERY.getMessage(), e);

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
			throw new NonRecoverableError(INCORRECT_COUNTER.getMessage(), e);

		}
		
		// Only one objectID can be retrieved
		if(numberOfValues != 1) {
	
			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError(CORRUPTED_COUNTER.getMessage());
	
		}
		
		// Close all DB connections
		try {

			resultSet.close();
			statement.close();

		} catch (SQLException e) {

			System.out.println(CONNECTION_LOST.getMessage());          	
			throw new NonRecoverableError(CONNECTION_LOST.getMessage(), e);

		}
		
		return documentId;
	}

	// Create the connection to the database
	DocumentIdProvider() throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned
		String path = System.getenv(ENVIRON);

		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError(UNDEFINED_ENVIRON.getMessage());

		} else {

			Properties propertiesInFile = getConfig(path);

			getConnection(propertiesInFile, "com.mysql.jdbc.Driver");
			
			getCurrentId();

		}
	}
	
	// Another constructor for testing purposes
	DocumentIdProvider(Properties properties) throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned
		String path = System.getenv(ENVIRON);

		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError(UNDEFINED_ENVIRON.getMessage());

		} else {

			getConnection(properties, "com.mysql.jdbc.Driver");
			
			getCurrentId();

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
			throw new NonRecoverableError(CANNOT_UPDATE_COUNTER.getMessage(), e);

		}

		// Check that the update has been effectively completed
		if (numUpdatedRows != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError(CORRUPTED_COUNTER.getMessage());

		}

		return documentId;

	}
}
