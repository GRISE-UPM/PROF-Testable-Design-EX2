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
	private static String ENVIRON  = "APP_HOME";
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";

	// ID for the newly created documents
	protected int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

	// Singleton access
	private static DocumentIdProvider instance;

	public static DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)
			return instance;

		instance = new DocumentIdProvider();
		return instance;
	}

	// Create the connection to the database
	private DocumentIdProvider() throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned
		String path = System.getenv(ENVIRON);
		
		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		} 

		createDocumentId(path);
	}

	protected DocumentIdProvider(int documentId) {
		this.documentId = documentId;
	}
	
	protected void createDocumentId(String path) throws NonRecoverableError {

		Properties propertiesInFile = new Properties();
	
		// Load the property file
		loadProperties(path, propertiesInFile);

		// Get the DB username and password
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");
	
		// Load DB driver
		loadDbDriver(DRIVER_NAME);
	
		// Create DB connection
		createDbConnection(url, username, password);
	
		// Read from the COUNTERS table
		String query = "SELECT documentId FROM Counters";
		ResultSet resultSet = readCountersFromDb(query);
	
		// Get the last objectID
		int numberOfValues = findLastDocumentId(resultSet);
	
		// Only one objectID can be retrieved
		verifyNumberOfValues(numberOfValues);
	
	}

	protected void loadProperties(String path, Properties propertiesInFile) throws NonRecoverableError {
		try {
			InputStream inputFile = new FileInputStream(path + "config.properties");
			propertiesInFile.load(inputFile);
	
		} catch (FileNotFoundException e) {
	
			System.out.println(NON_EXISTING_FILE.getMessage());          	
			throw new NonRecoverableError();
	
		} catch (IOException e) {
	
			System.out.println(CANNOT_READ_FILE.getMessage());          	
			throw new NonRecoverableError();
	
		}
	}

	protected void loadDbDriver(String driverName) throws NonRecoverableError {
		try {
	
			Class.forName(driverName).newInstance();
	
		} catch (InstantiationException | IllegalAccessException e) {
	
			System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
			throw new NonRecoverableError();
	
		} catch (ClassNotFoundException e) {
	
			System.out.println(CANNOT_FIND_DRIVER.getMessage());          	
			throw new NonRecoverableError();
	
		}
	}

	protected void createDbConnection(String url, String username, String password) throws NonRecoverableError {
		try {
	
			connection = DriverManager.getConnection(url, username, password);
	
		} catch (SQLException e) {
	
			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();
	
		}
	}

	protected ResultSet readCountersFromDb(String query) throws NonRecoverableError {
		
		Statement statement = null;

		try {
	
			statement = connection.createStatement();
			return statement.executeQuery(query);

		} catch (SQLException e) {
	
			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError();
	
		} finally {
			try {
				// Close DB connection
				statement.close();

			} catch(SQLException e) {
	
				System.out.println(CONNECTION_LOST.getMessage());          	
				throw new NonRecoverableError();

			}
		}
	}

	protected int findLastDocumentId(ResultSet resultSet) throws NonRecoverableError {
		int numberOfValues = 0;
		try {
	
			while (resultSet.next()) {
	
				documentId = resultSet.getInt("documentId");
				numberOfValues++;
	
			}
	
		} catch (SQLException e) {
			
			System.out.println(INCORRECT_COUNTER.getMessage());          	
			throw new NonRecoverableError();
			
		} finally {

			try {

				// Close DB connection
				resultSet.close();

			} catch(SQLException e) {
				
				System.out.println(CONNECTION_LOST.getMessage());          	
				throw new NonRecoverableError();

			}

		}

		return numberOfValues;
	}

	protected void verifyNumberOfValues(int numberOfValues) throws NonRecoverableError {
		// Only one objectID can be retrieved
		if(numberOfValues != 1) {
	
			System.out.println(CORRUPTED_COUNTER.getMessage());          	
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
		verifyNumberOfValues(numUpdatedRows);

		return documentId;

	}
}
