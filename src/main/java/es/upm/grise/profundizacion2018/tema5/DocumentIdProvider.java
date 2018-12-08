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
	static String CONFIG_FILE  = "config.properties";
	static String CONNECTION_DRIVER  = "com.mysql.jdbc.Driver";
	static String QUERY = "UPDATE Counters SET documentId = ?";
	protected Properties propertiesInFile;
	protected InputStream inputFile;
	// ID for the newly created documents
	protected int documentId;
	protected int numberOfvalues = 0;
	// Connection to database (open during program execution)
	Connection connection = null;

	// Singleton access
	protected static DocumentIdProvider instance;

	public DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)
			return instance;
		else {
			instance = new DocumentIdProvider();
			instance.connectDB();
			return instance;
		}
	}

	protected String getEnvironment() {
		// If ENVIRON does not exist, null is returned
		return System.getenv(ENVIRON);
	}

	protected void getMySQLDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName(CONNECTION_DRIVER).newInstance();
	}

	protected void getFile(String path) throws FileNotFoundException, IOException {
		inputFile = new FileInputStream(path + CONFIG_FILE);
		propertiesInFile.load(inputFile);
	}

	protected Properties getProperties() throws NonRecoverableError {
		String path = getEnvironment();
		if (path == null) {
			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();
		} else {
			propertiesInFile = new Properties();
			inputFile = null;

			// Load the property file
			try {
				getFile(getEnvironment());
			} catch (FileNotFoundException e) {

				System.out.println(NON_EXISTING_FILE.getMessage());
				throw new NonRecoverableError();

			} catch (IOException e) {

				System.out.println(CANNOT_READ_FILE.getMessage());
				throw new NonRecoverableError();

			}

			return propertiesInFile;
		}
	}
	// Create the connection to the database
	protected void connectDB() throws NonRecoverableError {
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
				throw new NonRecoverableError();

			} catch (IllegalAccessException e) {

				System.out.println(CANNOT_INSTANTIATE_DRIVER.getMessage());          	
				throw new NonRecoverableError();

			} catch (ClassNotFoundException e) {

				System.out.println(CANNOT_FIND_DRIVER.getMessage());          	
				throw new NonRecoverableError();

			}

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
			try {

				while (resultSet.next()) {

					documentId = resultSet.getInt("documentId");
					numberOfvalues++;

				}

			} catch (SQLException e) {

				System.out.println(INCORRECT_COUNTER.getMessage());          	
				throw new NonRecoverableError();

			}

			// Only one objectID can be retrieved
			if(numberOfvalues != 1) {

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
		String query = QUERY;
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
