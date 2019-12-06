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
	public int numberOfValues;
	Properties propertiesInFile;

	// Connection to database (open during program execution)
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

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
		propertiesInFile = new Properties();
		String path = devuelveEntorno(); 
		InputStream inputFile = null;
		ficheroProperties(inputFile,path);
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");
		cargarDriver();
		conexionBD(url,username,password);
		leerTablaCounters();
		conseguirLastID();
		checkearID();
	}
	
	private void todo() throws NonRecoverableError {
		// If ENVIRON does not exist, null is returned
			String path = devuelveEntorno(); 

			InputStream inputFile = null;

			// Load the property file
			ficheroProperties(inputFile,path);

			

			// Get the DB username and password
			String url = propertiesInFile.getProperty("url");
			String username = propertiesInFile.getProperty("username");
			String password = propertiesInFile.getProperty("password");

			// Load DB driver
			cargarDriver();

			// Create DB connection
			conexionBD(url,username,password);

			// Read from the COUNTERS table
			leerTablaCounters();

			// Get the last objectID
			conseguirLastID();

			// Only one objectID can be retrieved
			checkearID();

			// Close all DB connections
			cerrarConexion();

	}

	private String devuelveEntorno () throws NonRecoverableError {
		String path = System.getenv(ENVIRON);
		
		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		}
		return path;
	}
	
	protected void ficheroProperties(InputStream inputFile, String path) throws NonRecoverableError {
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
	}
	
	protected void cargarDriver() throws NonRecoverableError {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

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
	
	private void conexionBD (String url, String username, String password) throws NonRecoverableError {
		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {

			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	private void leerTablaCounters() throws NonRecoverableError {
		String query = "SELECT documentId FROM Counters";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {

			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	private void conseguirLastID () throws NonRecoverableError {
		numberOfValues = 0;
		try {

			while (resultSet.next()) {

				documentId = resultSet.getInt("documentId");
				numberOfValues++;

			}

		} catch (SQLException e) {

			System.out.println(INCORRECT_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	protected void checkearID() throws NonRecoverableError {
		if(numberOfValues != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	private void cerrarConexion() throws NonRecoverableError {
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
