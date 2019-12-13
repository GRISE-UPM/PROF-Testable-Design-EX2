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

	private Properties propertiesInFile = new Properties();
	private String path = System.getenv(ENVIRON);
	private Statement statement = null;
	private ResultSet resultSet = null;

	public static DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {

			instance = new DocumentIdProvider();
			return instance;

		}	
	}

	DocumentIdProvider() {

	}

	// Create the connection to the database
	public void createConnectionToDB() throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned

		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		} else {

			loadPropertyFile(path + "config.properties");

			loadDBDriver("com.mysql.jdbc.Driver");

			createDBConnection(); // Acceso a DB

			readCountersTable(); // Acceso a DB

			int numberOfValues = getLastObjectID(); // Acceso a DB

			// Only one objectID can be retrieved
			if(numberOfValues != 1) {

				System.out.println(CORRUPTED_COUNTER.getMessage());          	
				throw new NonRecoverableError();

			}

			closeDBConnection(); // Acceso a DB
		}
	}

	void loadPropertyFile(String path) throws NonRecoverableError {
		// Load the property file
		InputStream inputFile = null;
		try {
			inputFile = new FileInputStream(path);
			propertiesInFile.load(inputFile);

		} catch (FileNotFoundException e) {

			System.out.println(NON_EXISTING_FILE.getMessage());          	
			throw new NonRecoverableError();

		} catch (IOException e) {

			System.out.println(CANNOT_READ_FILE.getMessage());          	
			throw new NonRecoverableError();

		}
	}

	void loadDBDriver(String driver) throws NonRecoverableError {
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

	void createDBConnection() throws NonRecoverableError {
		// Get the DB username and password
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");
		// Create DB connection
		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {

			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();

		}
	}

	void readCountersTable() throws NonRecoverableError {
		// Read from the COUNTERS table
		String query = "SELECT documentId FROM Counters";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {

			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError();

		}
	}

	int getLastObjectID() throws NonRecoverableError {
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

	void closeDBConnection() throws NonRecoverableError {
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
		int numUpdatedRows = updateDocumentIDCounter();

		// Check that the update has been effectively completed
		if (numUpdatedRows != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}

		return documentId;

	}

	int updateDocumentIDCounter() throws NonRecoverableError {

		// Update the documentID counter
		int numUpdatedRows = 0;
		try {

			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Counters SET documentId = ?");
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
