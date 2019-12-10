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
	protected static String ENVIRON = "APP_HOME";

	// ID for the newly created documents
	protected int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

	// Singleton access
	protected static DocumentIdProvider instance;

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

		// If ENVIRON does not exist, null is returned
		String path = System.getenv(ENVIRON);
		if (path == null) {
			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();
		}

		Properties propertiesInFile = getProperties(path);

		// Get the DB username and password
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");

		loadDbDriver("com.mysql.jdbc.Driver");
		createDbconnection(url, username, password);
		readFromCountersTable();
	}

	// Carga el fichero de propiedades
	protected Properties getProperties(String path) throws NonRecoverableError {

		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;

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

	// Carga el driver de la DB
	protected void loadDbDriver(String driver) throws NonRecoverableError {

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

	// Crea conexión a la DB
	protected void createDbconnection(String url, String username, String password) throws NonRecoverableError {

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());
			throw new NonRecoverableError();
		}
	}

	// Lee de la tabla
	protected void readFromCountersTable() throws NonRecoverableError {

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

		int numberOfValues = 0;
		try {
			while (resultSet.next()) {
				documentId = resultSet.getInt("documentId");
				numberOfValues++;
			}
		} catch (SQLException e) {
			System.out.println(INCORRECT_COUNTER.getMessage());
		}

		if (numberOfValues != 1) {
			System.out.println(CORRUPTED_COUNTER.getMessage());
			throw new NonRecoverableError();
		}

		try {
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println(CONNECTION_LOST.getMessage());
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

	// Devuelve ID del ultimo
	protected int last(ResultSet resultSet, boolean checker) throws NonRecoverableError {
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

}
