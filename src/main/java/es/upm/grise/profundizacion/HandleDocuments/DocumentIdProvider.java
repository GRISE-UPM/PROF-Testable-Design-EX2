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

	// ID for the newly created documents
	private int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

	protected Statement statement = null;
	protected ResultSet resultSet = null;
	
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


	// Create the connection to the database
	protected DocumentIdProvider() throws NonRecoverableError {

		String path = getPath(ENVIRON);
		
		
		Properties propertiesInFile = loadProperties(path);
		
		// Get the DB username and password
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");
		
		loadDbDriver("com.mysql.jdbc.Driver");
		createDbconnection(url, username, password);
		readFromCountersTable();
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
	
	protected String getPath(String environ) throws NonRecoverableError {
		String path = System.getenv(environ);
		
		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		}
		 return path;
	}
	
	protected Properties loadProperties(String path) throws NonRecoverableError {
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;

		// Load the property file
		try {
			inputFile = new FileInputStream(path + "config.properties");
			propertiesInFile.load(inputFile);

		} catch (FileNotFoundException e) {

			System.out.println(NON_EXISTING_FILE.getMessage());          	
			throw new NonRecoverableError();

		} catch (IOException e) {

			System.out.println(CANNOT_READ_FILE.getMessage());          	
			throw new NonRecoverableError();

		}
		return propertiesInFile;
	}
	
	protected void loadDbDriver(String driver) throws NonRecoverableError{
		// Load DB driver
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
	
	protected void createDbconnection (String url, String username, String password) throws NonRecoverableError{
		// Create DB connection
		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {

			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	protected void readFromCountersTable() throws NonRecoverableError{
		// Read from the COUNTERS table
		String query = "SELECT documentId FROM Counters";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {

			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError();

		}
		
		
		if(getLast(resultSet, true) != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}
		closeDbConnections(resultSet, statement);
	}
	
	protected int getLast(ResultSet resultSet, boolean checker) throws NonRecoverableError{
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
		
		return numberOfValues;
		
	}
	
	protected void closeDbConnections(ResultSet resultSet, Statement statement) throws NonRecoverableError{
		// Close all DB connections
		try {

			resultSet.close();
			statement.close();

		} catch (SQLException e) {

			System.out.println(CONNECTION_LOST.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
}