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
	static String DRIVER = "com.mysql.jdbc.Driver";
	private static String ENVIRON  = "APP_HOME";

	// ID for the newly created documents
	private int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

	// Singleton access
	static DocumentIdProvider instance;

	public static DocumentIdProvider getInstance(String driver) throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {

			instance = new DocumentIdProvider(driver);
			return instance;

		}	
	}

	// Create the connection to the database
	DocumentIdProvider(String driver) throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned
		String path = System.getenv(ENVIRON);

		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		} else {

			Properties propertiesInFile = readProp(path);
			connection = readFromDB(driver, propertiesInFile);
			//InputStream inputFile = null;
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
		}
	}

	Properties readProp(String path) throws NonRecoverableError {
		Properties propertiesInFile  = new Properties();
		InputStream inputFile = null;
		try {

			inputFile = new FileInputStream(path + "config.properties");
			propertiesInFile .load(inputFile);

		} catch (FileNotFoundException e) {

			throw new NonRecoverableError(NON_EXISTING_FILE.getMessage());

		} catch (IOException e) {

			throw new NonRecoverableError(CANNOT_READ_FILE.getMessage());
		}

		return propertiesInFile ;
	}

	Connection readFromDB(String driver, Properties prop) throws NonRecoverableError{

		Connection connection = null;

		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");

		// Load DB driver
		try {

			Class.forName(driver).newInstance();

		} catch (InstantiationException e) {

			throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage());

		} catch (IllegalAccessException e) {

			throw new NonRecoverableError(CANNOT_INSTANTIATE_DRIVER.getMessage());

		} catch (ClassNotFoundException e) {

			throw new NonRecoverableError(CANNOT_FIND_DRIVER.getMessage());
		}

		// Create DB connection
		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {

			throw new NonRecoverableError(CANNOT_CONNECT_DATABASE.getMessage());

		}
		return connection;
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
