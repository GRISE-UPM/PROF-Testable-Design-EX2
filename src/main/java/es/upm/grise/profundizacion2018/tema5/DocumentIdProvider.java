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
	private static String ENVIRON = "APP_HOME";

	// ID for the newly created documents
	// private int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

	// Singleton access
	// private static DocumentIdProvider instance;

	// New
	static String CONFIGFILE = "config.properties";
	static String DRIVER = "com.mysql.jdbc.Driver";
	static String UPDATE_QUERY = "UPDATE Counters SET documentId = ?";
	int documentId;
	int numberOfValues = 0;
	static DocumentIdProvider instance;

	// static eliminado
	public DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {

			instance = new DocumentIdProvider();
			instance.connect();
			return instance;

		}	
	}

	void connect() throws NonRecoverableError {
		String path = System.getenv(ENVIRON);

		if (path == null) {
			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();
		} else {
			Properties propertiesInFile = new Properties();
			InputStream inputFile = null;

			try {
				inputFile = new FileInputStream(path + CONFIGFILE);
				propertiesInFile.load(inputFile);
			} catch (IOException e) {
				System.out.println(CANNOT_READ_FILE.getMessage());
				throw new NonRecoverableError();
			}

			String url = propertiesInFile.getProperty("url");
			String username = propertiesInFile.getProperty("username");
			String password = propertiesInFile.getProperty("password");

			// Load DB driver
			try {
				Class.forName(DRIVER).newInstance();
			
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Create DB connection
			try {
				connection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				System.out.println(CANNOT_CONNECT_DATABASE.getMessage());
				throw new NonRecoverableError();
			}

			String query = "SELECT documentId FROM Counters";
			Statement statement = null;
			ResultSet resultSet = null;

			// Read from the COUNTERS table
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
					numberOfValues++;
				}
			} catch (SQLException e) {
				System.out.println(INCORRECT_COUNTER.getMessage());
				throw new NonRecoverableError();
			}
			if (numberOfValues != 1) {
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
