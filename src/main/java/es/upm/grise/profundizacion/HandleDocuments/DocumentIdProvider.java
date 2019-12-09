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
	private static String BBDD_DRIVER = "com.mysql.jdbc.Driver";

	// ID for the newly created documents
	private int documentId;
	
	private Properties propertiesInFile = new Properties();
	private Statement statement = null;
	protected ResultSet resultSet = null;
	protected String path;
	

	// Connection to database (open during program execution)
	Connection connection = null;

	// Singleton access
	private static DocumentIdProvider instance;

	public static DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {
			instance = new DocumentIdProvider();
			instance.CreateConnectionDatabase();
			return instance;
		}	
	}
	
	public void GetPropertyFile(String path) throws NonRecoverableError {
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
	}
	
	public void LoadBBDD_Driver(String bbdd_Driver) throws NonRecoverableError {
		// Load DB driver
		try {

			Class.forName(bbdd_Driver).newInstance();

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
	
	public void ReadCountersTable() throws NonRecoverableError {
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
	
	public int GetLastObjectID() throws NonRecoverableError {
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
	
	public DocumentIdProvider() { } //CONSTRUCTOR VACIO
	

	// METODO ESPECIFICO PARA ESTABLECER LA CONEXION A LA BBDD
	// Create the connection to the database 
	public void CreateConnectionDatabase() throws NonRecoverableError {
		// If ENVIRON does not exist, null is returned
				path = System.getenv(ENVIRON);
		
		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		} else {
			GetPropertyFile(path); //Abre propiedades fichero
		
			// Get the DB username and password
			String url = propertiesInFile.getProperty("url");
			String username = propertiesInFile.getProperty("username");
			String password = propertiesInFile.getProperty("password");

			LoadBBDD_Driver(BBDD_DRIVER); //Carga el driver

			// Create DB connection
			try {

				connection = DriverManager.getConnection(url, username, password);

			} catch (SQLException e) {
				
				System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
				throw new NonRecoverableError();

			}
			
			ReadCountersTable(); //Lee la tabla de Counters
			
			// Only one objectID can be retrieved
			if(GetLastObjectID() != 1) {

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
	
	public int DocumentIDUpdate() throws NonRecoverableError {
		// Access the COUNTERS table
		String query = "UPDATE Counters SET documentId = ?";
		// Update the documentID counter
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, documentId);
			return preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.toString());
			System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	

	// Return the next valid objectID
	public int getDocumentId() throws NonRecoverableError {
		documentId++;

		int numUpdatedRows;

		numUpdatedRows = DocumentIDUpdate();

		// Check that the update has been effectively completed
		if (numUpdatedRows != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}

		return documentId;
	}
}
