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
	public static String ENVIRON  = "APP_HOME";

	// ID for the newly created documents
	private int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

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
	

	// Create the connection to the database
	protected DocumentIdProvider() throws NonRecoverableError{
	}
	
	protected void setIdToDocument() throws NonRecoverableError {
		// If ENVIRON does not exist, null is returned
		String path = System.getenv(ENVIRON);
		
		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		} else {
			Properties propertiesInFile = loadPropertiesFile(path);

			// Get the DB username and password
			String url = propertiesInFile.getProperty("url");
			String username = propertiesInFile.getProperty("username");
			String password = propertiesInFile.getProperty("password");


			getDriverManager();

			Connection connection = setConection(url, username, password);

			ResultSet resultSet = readCounters(connection);	
			
			int numberOfValues = getNumberOfDocs(resultSet);
			
			checkNrValues(numberOfValues);

			closeResult(resultSet);
			
		}
	}
	
	/**
	 * Metodo auxiliar para la obtención del fichero de configuración.
	 * @param path
	 * @return
	 */
	protected Properties loadPropertiesFile(String path) throws NonRecoverableError{
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
	
	/**
	 * Get the diverManager
	 * @throws NonRecoverableError
	 */
	protected void getDriverManager() throws NonRecoverableError{
		// Load DB driver
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
	
	protected Connection setConection(String url, String username, String password) throws NonRecoverableError {
		// Create DB connection
		Connection connection;
		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {

			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();

		}
		return connection;
	}
	
	protected ResultSet readCounters(Connection connection) throws NonRecoverableError {
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
		try {
			statement.close();

		} catch (SQLException e) {

			System.out.println(CONNECTION_LOST.getMessage());          	
			throw new NonRecoverableError();

		}
		return resultSet;
	}
	
	protected int getNumberOfDocs(ResultSet resultSet) throws NonRecoverableError {
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
	
	protected void checkNrValues(int numberOfValues) throws NonRecoverableError {
		// Only one objectID can be retrieved
		if(numberOfValues != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();

					}
	}
	
	protected void closeResult(ResultSet resultSet) throws NonRecoverableError {
		// Close all DB connections
		try {

			resultSet.close();

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
		int numUpdatedRows = runQuery(query);


		// Check that the update has been effectively completed
		if (numUpdatedRows != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}

		return documentId;

	}
	
	/**
	 * Metedo auxiliar para ejecutar queies.
	 * @param query
	 * @return
	 * @throws NonRecoverableError
	 */
	protected int runQuery(String query) throws NonRecoverableError{
		// Update the documentID counter
		int numUpdatedRows;
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, documentId);
			numUpdatedRows = preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.toString());
			System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}
		return numUpdatedRows;
	}
}
