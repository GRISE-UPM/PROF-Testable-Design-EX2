package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_CONNECT_DATABASE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_FIND_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_INSTANTIATE_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_READ_FILE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_RUN_QUERY;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CONNECTION_LOST;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.INCORRECT_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.NON_EXISTING_FILE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.UNDEFINED_ENVIRON;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DocumentIdProvider {

	// Environment variable
	private static String ENVIRON  = "APP_HOME";

	// ID for the newly created documents
	private int documentId;

	// Connection to database (open during program execution)
	private Connection connection = null;

	// Singleton access
	private static DocumentIdProvider instance;

	public static DocumentIdProvider getInstance() throws NonRecoverableError {

		if (instance != null) {

			return instance;
		} else {

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

		loadDatabaseDriver("com.mysql.jdbc.Driver");

		connection = createDatabaseConnection(url, username, password);

		documentId = readLastId();
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
			String message = CANNOT_UPDATE_COUNTER.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}

		// Check that the update has been effectively completed
		if (numUpdatedRows != 1) {

			String message = CORRUPTED_COUNTER.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}

		return documentId;
	}

	private String getPath(String environment) throws NonRecoverableError {

		String path = System.getenv(environment);

		if (path == null) {

			String message = UNDEFINED_ENVIRON.getMessage();
			System.out.println(message);
			throw new NonRecoverableError(message);
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

			String message = NON_EXISTING_FILE.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		} catch (IOException e) {

			String message = CANNOT_READ_FILE.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}

		return propertiesInFile;
	}

	protected void loadDatabaseDriver(String driverClass) throws NonRecoverableError {

		// Load DB driver
		try {

			Class.forName(driverClass).newInstance();

		} catch (ClassNotFoundException e) {

			String message = CANNOT_FIND_DRIVER.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		} catch (IllegalAccessException e) {

			String message = CANNOT_INSTANTIATE_DRIVER.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		} catch (InstantiationException e) {

			String message = CANNOT_INSTANTIATE_DRIVER.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}
	}

	private Connection createDatabaseConnection(String url, String username, String password) throws NonRecoverableError {

		// Create DB connection
		Connection connection;
		try {

			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {

			String message = CANNOT_CONNECT_DATABASE.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}

		return connection;
	}

	private int readLastId() throws NonRecoverableError {

		// Read from the COUNTERS table
		String query = "SELECT documentId FROM Counters";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {

			String message = CANNOT_RUN_QUERY.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}

		int lastId = getLastId(resultSet);

		closeResources(resultSet, statement);

		return lastId;
	}

	protected int getLastId(ResultSet resultSet) throws NonRecoverableError {

		// Get the last objectID
		int numberOfValues = 0;
		int lastId = 0;

		try {

			while (resultSet.next()) {

				lastId = resultSet.getInt("documentId");
				numberOfValues++;
			}
		} catch (SQLException e) {

			String message = INCORRECT_COUNTER.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}

		// Only one objectID can be retrieved
		if(numberOfValues != 1) {

			String message = CORRUPTED_COUNTER.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}

		return lastId;
	}

	private void closeResources(ResultSet resultSet, PreparedStatement statement) throws NonRecoverableError {

		// Close all DB connections
		try {

			resultSet.close();
			statement.close();

		} catch (SQLException e) {

			String message = CONNECTION_LOST.getMessage();
			System.out.println(message);          	
			throw new NonRecoverableError(message);
		}
	}
}
