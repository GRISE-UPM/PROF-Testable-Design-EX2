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
	static String ENVIRON = "APP_HOME";

	// ID for the newly created documents
	int documentId;

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
	public DocumentIdProvider () {}

	public DocumentIdProvider (int id) { this.documentId=id; }

	// Create the connection to the database (EL ANTIGUO CONSTRUCTOR)
	public void DocumentIdProviderFile() throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned
		String path = System.getenv(ENVIRON);
		//System.out.println("path= "+ENVIRON);
		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		}else {

			Properties propertiesInFile = new Properties();
			InputStream inputFile = null;

			// Load the property file
			inputFile = propertyFile(path, propertiesInFile);



				// Get the DB username and password
				String url = propertiesInFile.getProperty("url");
				String username = propertiesInFile.getProperty("username");
				String password = propertiesInFile.getProperty("password");

				// Load DB driver
				loadDriver("com.mysql.jdbc.Driver");

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
				objectID(resultSet);

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

		//load the properties file

		public InputStream propertyFile (String path, Properties propertiesInFile
	) throws NonRecoverableError{
			InputStream inputFile;
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
			return inputFile;

		}

	// Load DB driver

	public void loadDriver(String driver) throws NonRecoverableError {
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

		// Return the next valid objectID
		public int getDocumentId () throws NonRecoverableError {

			documentId++;

			int numUpdatedRows = updateID();


			checkUpdate(numUpdatedRows);

			return documentId;

		}

		public int updateID() throws NonRecoverableError {
			// Access the COUNTERS table
			String query = "UPDATE Counters SET documentId = ?";
			int numUpdatedRows = 0;

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
		return numUpdatedRows;
		}

		// Check that the update has been effectively completed
		public void checkUpdate (int numUpdatedRows) throws NonRecoverableError {
			if (numUpdatedRows != 1) {

				System.out.println(CORRUPTED_COUNTER.getMessage());
				throw new NonRecoverableError();
			}

		}

		//get last object id

		public void objectID (ResultSet rs) throws NonRecoverableError {
			int numberOfValues = 0;
			try {

				while (rs.next()) {

					documentId = rs.getInt("documentId");
					numberOfValues++;

				}

			} catch (SQLException e) {

				System.out.println(INCORRECT_COUNTER.getMessage());
				throw new NonRecoverableError();

			}// Only one objectID can be retrieved
			if (numberOfValues != 1) {

				System.out.println(CORRUPTED_COUNTER.getMessage());
				throw new NonRecoverableError();

			}

		}
	}

