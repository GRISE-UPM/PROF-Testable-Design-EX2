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

	// NEW: Constructor protected, antes private
	// Create the connection to the database
	protected DocumentIdProvider() throws NonRecoverableError {

		
		// NEW: se realiza la comprobacion de variable de entorno en envVar
		// If ENVIRON does not exist, null is returned
		String path = envVar();
		
		// NEW: se ha quitado el else
		
		// NEW: el bloque de "Load the property File" se sustituye por una llamada a propLoader
		Properties propertiesInFile = propLoader(path);

		// Get the DB username and password
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");

		// NEW: se sustituye el bloque de "Load DB Driver" por una llamada a driverLoader
		driverLoader("com.mysql.jdbc.Driver");

		// NEW: el bloque "Create DB connection" se sustituye por una llamada a connCreator
		connection = connCreator(url, username, password);

		// NEW: se sustiuyen las llamadas a base de datos por una llamada al metodo getID
		documentId = getID();
	}
	
	// NEW
	protected String envVar() throws NonRecoverableError {
		String path = System.getenv(ENVIRON);
		
		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		}
		
		return path;
	}
	
	// NEW
	protected Properties propLoader(String path) throws NonRecoverableError {
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
	
	// NEW
	protected void driverLoader(String driver) throws NonRecoverableError{
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
	
	// NEW
	protected Connection connCreator(String url, String username, String password) throws NonRecoverableError {
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();
		}
		return connection;
	}
	
	// NEW 
	protected int getID() throws NonRecoverableError {
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
		return documentId;
	}
	
	// Return the next valid objectID
	protected int getDocumentId() throws NonRecoverableError {

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
