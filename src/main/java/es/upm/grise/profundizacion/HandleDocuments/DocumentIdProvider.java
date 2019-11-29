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
	protected static String ENVIRON  = "APP_HOME";

	// ID for the newly created documents
	protected int documentId;

	// Connection to database (open during program execution)
	Connection connection = null;

	//contador numero de documentos
	protected int numberOfValues;
	//contador para crear los ids de los documentos
	protected int dcId;
	
	//atributos para probar el read counters from table
	Statement statement = null;
	ResultSet resultSet = null;
	
	//quitamos el singleton acces
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
	
	//SE ha quitado toda la logica del constriuctor, y se ha mentido en la funcion realizar todo
	protected DocumentIdProvider() throws NonRecoverableError {}

	// Create the connection to the database
	protected void realizarTodo() throws NonRecoverableError {

		// If ENVIRON does not exist, null is returned
		String path=comprobarEnviorment();
		 //else {

			Properties propertiesInFile = new Properties();
			InputStream inputFile = null;

			// Load the property file
			loadPropertyFile(path,inputFile,propertiesInFile);
			

			// Get the DB username and password
			String url = propertiesInFile.getProperty("url");
			String username = propertiesInFile.getProperty("username");
			String password = propertiesInFile.getProperty("password");

			// Load DB driver
			String driverDB="com.mysql.jdbc.Driver";
			loadDBdriver(driverDB);


			// Create DB connection
			createDBconnection(url,username,password);
			

			// Read from the COUNTERS table
			String query = "SELECT documentId FROM Counters";
			readCountersTable(query);
			
			
			
			// Get the last objectID
			numberOfValues=getLastObjectID(resultSet,documentId);
			

			// Only one objectID can be retrieved
			//es innecesario por ser sencillo, pero así nos aseguramos de que es testeable
			checkNumberOfValues(numberOfValues);
			

			// Close all DB connections
			closeAllDBConnections(resultSet,statement);

		//}
	}









	protected String comprobarEnviorment() throws NonRecoverableError {
		// TODO Auto-generated method stub
		String path = System.getenv(ENVIRON);

		if (path == null) {

			System.out.println(UNDEFINED_ENVIRON.getMessage());
			throw new NonRecoverableError();

		}
		return path;
		
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
	
	
	
	
	protected void loadPropertyFile(String path, InputStream inputFile, Properties propertiesInFile) throws NonRecoverableError {
		// TODO Auto-generated method stub
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
	
	protected void loadDBdriver(String driverDB) throws NonRecoverableError {
		// TODO Auto-generated method stub
		try {

			Class.forName("driverDB").newInstance();

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
	
	protected void createDBconnection(String url, String username, String password) throws NonRecoverableError {
		// TODO Auto-generated method stub
		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {

			System.out.println(CANNOT_CONNECT_DATABASE.getMessage());          	
			throw new NonRecoverableError();

		}
		
	}
	
	protected void readCountersTable(String query) throws NonRecoverableError {
		// TODO Auto-generated method stub
		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {

			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError();

		}
		
	}
	
	protected int getLastObjectID(ResultSet resultSet2, int documentId2) throws NonRecoverableError {
		// TODO Auto-generated method stub
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
	
	protected void checkNumberOfValues(int numberOfValues2) throws NonRecoverableError {
		// TODO Auto-generated method stub
		if(numberOfValues2 != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}
		
	}
	
	protected void closeAllDBConnections(ResultSet resultSet2, Statement statement2) throws NonRecoverableError {
		// TODO Auto-generated method stub
		try {

			resultSet2.close();
			statement2.close();

		} catch (SQLException e) {

			System.out.println(CONNECTION_LOST.getMessage());          	
			throw new NonRecoverableError();

		}
	}

	
}
