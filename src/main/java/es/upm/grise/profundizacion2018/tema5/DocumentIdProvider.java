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
import java.util.Map;
import java.util.Properties;

public class DocumentIdProvider {

	// Environment variable
	String ENVIRON  = "APP_HOME";
	String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	// ID for the newly created documents
	int documentId;

	// Connection to DB
	Connection connection = null;

	String SELECT_QUERY = "SELECT documentId FROM Counters";
	String UPDATE_QUERY = "UPDATE Counters SET documentId = ?";

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

	DocumentIdProvider() throws NonRecoverableError {
		String path = loadPathFromEnv();
		Properties propertiesInFile = loadPropertiesFromFile(path);
		loadDbConnectionFromProperties(propertiesInFile, MYSQL_DRIVER);
		readLastDocumentId();
	}


	String loadPathFromEnv() throws NonRecoverableError {
		String path = System.getenv(ENVIRON);
		if (path == null) {
			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();
		}
		return null;
	}

	Properties loadPropertiesFromFile(String path) throws NonRecoverableError {
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;
		try {
			inputFile = new FileInputStream(path + "config.properties");
			propertiesInFile.load(inputFile);

		} catch (FileNotFoundException e) {
			throw new NonRecoverableError(NON_EXISTING_FILE.getMessage());

		} catch (IOException e) {
			throw new NonRecoverableError(CANNOT_READ_FILE.getMessage());
		}

		return propertiesInFile;
	}

	void loadDbConnectionFromProperties(Properties properties, String driver) throws NonRecoverableError{

		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");

		// Load DB driver
		try {
			Class.forName(driver).newInstance();

		} catch (InstantiationException e) {
			throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage());

		} catch (IllegalAccessException e) {
			throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage());

		} catch (ClassNotFoundException e) {
			throw new NonRecoverableError(CANNOT_FIND_DRIVER.getMessage());
		}

		// Create DB connection
		try {
			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			throw new NonRecoverableError(CANNOT_CONNECT_DATABASE.getMessage());

		}
	}

	void readLastDocumentId() throws NonRecoverableError{
		// Read from the COUNTERS table
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SELECT_QUERY);

		} catch (SQLException e) {
			throw new NonRecoverableError(CANNOT_RUN_QUERY.getMessage());

		}

		// Get the last objectID
		int numberOfValues = 0;
		try {
			while (resultSet.next()) {
				documentId = resultSet.getInt("documentId");
				numberOfValues++;

			}

		} catch (SQLException e) {
			throw new NonRecoverableError(INCORRECT_COUNTER.getMessage());

		}

		// Only one objectID can be retrieved
		if(numberOfValues != 1) {
			throw new NonRecoverableError(CORRUPTED_COUNTER.getMessage());

		}
		// Close all DB connections
		try {
			resultSet.close();
			statement.close();

		} catch (SQLException e) {
			throw new NonRecoverableError(CONNECTION_LOST.getMessage());

		}
	}



	// Return the next valid objectID
	public int getDocumentId() throws NonRecoverableError {

		documentId++;

		// Access the COUNTERS table
		int numUpdatedRows;

		// Update the documentID counter
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
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
