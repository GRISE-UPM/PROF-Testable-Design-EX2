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
	private static String ENVIRON  = "APP_HOME";
	
	// Other Variables
	public String CONFIG_FILE_NAME = "config.properties";
	public String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	public int numberOfValues;
	
	Properties propertiesInFile;
	InputStream inputFile;

	// ID for the newly created documents
	protected int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	// Singleton access
	protected static DocumentIdProvider instance;

	public DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {

			instance = new DocumentIdProvider();
			return instance;

		}	
	}
	
	protected void getMySQLDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName(DATABASE_DRIVER).newInstance();
	}
	
	protected void getConnection(String url, String username, String password) throws SQLException {
		connection = DriverManager.getConnection(url, username, password);
	}
	
	protected void executeQuery(String query) throws SQLException {
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
	}
	
	protected void getDocumentIdFromDB() throws SQLException {
		while (resultSet.next()) {

			documentId = resultSet.getInt("documentId");
			numberOfValues++;

		}
	}
	
	protected void closeDBConnections() throws SQLException {
		resultSet.close();
		statement.close();
	}
	
	protected void connectToDB() throws NonRecoverableError {
		Properties propertiesInFile = getProperties();
		
		// Get the DB username and password
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");

		// Load DB driver
		try {

			getMySQLDriver();

		} catch (InstantiationException e) {

			System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
			throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage());

		} catch (IllegalAccessException e) {

			System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
			throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage());
			
		} catch (ClassNotFoundException e) {

			System.out.println(CANNOT_FIND_DRIVER.getMessage());          	
			throw new NonRecoverableError(CANNOT_FIND_DRIVER.getMessage());

		}

		// Create DB connection
		try {

			getConnection(url,username,password);

		} catch (SQLException e) {

			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError(CANNOT_CONNECT_DATABASE.getMessage());

		}

		// Read from the COUNTERS table
		String query = "SELECT documentId FROM Counters";

		try {

			executeQuery(query);

		} catch (SQLException e) {

			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError(CANNOT_RUN_QUERY.getMessage());

		}
			
		// Get the last objectID
		numberOfValues = 0;
		try {

			getDocumentIdFromDB();

		} catch (SQLException e) {

			System.out.println(INCORRECT_COUNTER.getMessage());          	
			throw new NonRecoverableError(INCORRECT_COUNTER.getMessage());

		}

		// Only one objectID can be retrieved
		if(numberOfValues != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError(CORRUPTED_COUNTER.getMessage());

		}

		// Close all DB connections
		try {

			closeDBConnections();

		} catch (SQLException e) {

			System.out.println(CONNECTION_LOST.getMessage());          	
			throw new NonRecoverableError(CONNECTION_LOST.getMessage());

		}
	}
	
	protected String getEnvironment() {
		return System.getenv(ENVIRON);
	}
	
	private Properties getProperties() throws NonRecoverableError {
		String path = getEnvironment();

		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError(UNDEFINED_ENVIRON.getMessage());

		} else {

			propertiesInFile = new Properties();
			inputFile = null;

			// Load the property file
			try {
				
				getFile(path);

			} catch (FileNotFoundException e) {

				System.out.println(NON_EXISTING_FILE.getMessage());          	
				throw new NonRecoverableError(NON_EXISTING_FILE.getMessage());

			} catch (IOException e) {

				System.out.println(CANNOT_READ_FILE.getMessage());          	
				throw new NonRecoverableError(CANNOT_READ_FILE.getMessage());

			}
			
		}
		
		return propertiesInFile;
	}
	
	protected void getFile(String path) throws FileNotFoundException,IOException {
		inputFile = new FileInputStream(path + CONFIG_FILE_NAME);;
		propertiesInFile.load(inputFile);
	}

	// Create the connection to the database
	protected DocumentIdProvider() throws NonRecoverableError {
			connectToDB();
	}
	
	protected int updateCounter() throws SQLException {
		String query = "UPDATE Counters SET documentId = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, documentId);
		int numUpdatedRows = preparedStatement.executeUpdate();
		return numUpdatedRows;
	}

	// Return the next valid objectID
	public int getDocumentId() throws NonRecoverableError {

		documentId++;

		// Access the COUNTERS table
		int numUpdatedRows;

		// Update the documentID counter
		try {

			numUpdatedRows = updateCounter();

		} catch (SQLException e) {

			System.out.println(e.toString());
			System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError(CANNOT_UPDATE_COUNTER.getMessage());

		}

		// Check that the update has been effectively completed
		if (numUpdatedRows != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError(CORRUPTED_COUNTER.getMessage());

		}

		return documentId;

	}
}
