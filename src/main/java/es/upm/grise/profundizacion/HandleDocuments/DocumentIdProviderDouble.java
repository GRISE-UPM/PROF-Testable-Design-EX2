package es.upm.grise.profundizacion.HandleDocuments;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_FIND_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_INSTANTIATE_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_READ_FILE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CONNECTION_LOST;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.INCORRECT_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.NON_EXISTING_FILE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DocumentIdProviderDouble extends DocumentIdProvider{
	
	int idDocumento = 1;
	static DocumentIdProviderDouble con;

	public DocumentIdProviderDouble() throws NonRecoverableError {
		super();
	}
	
	@Override
	protected String getPath()throws NonRecoverableError{
		return super.getPath();
	}
	
	@Override
	protected void getProperties(Properties propertiesInFile,InputStream inputFile)  throws NonRecoverableError{
		String path = "Prueba";

		// Load the property file
		try {
			inputFile = new FileInputStream(Paths.get(path, "config.properties").toString());
			propertiesInFile.load(inputFile);

		} catch (FileNotFoundException e) {

			System.out.println(NON_EXISTING_FILE.getMessage());          	
			throw new NonRecoverableError();

		} catch (IOException e) {

			System.out.println(CANNOT_READ_FILE.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	@Override
	protected void loadDriver () throws NonRecoverableError {
		// Get the DB username and password
	 try {
		// Load DB driver
        Class.forName("prueba.Driver").newInstance();
	  }catch (InstantiationException e) {

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
	
	@Override
	protected void createConnection (String url,String username, String password)throws NonRecoverableError {}
	
	@Override
	protected ResultSet executeQuery (Connection connection)throws NonRecoverableError {
		return super.executeQuery(connection);
	}
	
	@Override
	protected DocumentIdProvider load() throws NonRecoverableError{
		return super.load();
	}
	
	@Override
	protected int getlastObjectId() throws NonRecoverableError{
		// Get the last objectID
		boolean prueba = false;
		if (prueba) {
		int numberOfValues = 0;
		int id = 0;
	    load();
		ResultSet resultSet = executeQuery(connection);
		if (resultSet == null) {
			return id;
		}
		try {

			while (resultSet.next()) {

				id = resultSet.getInt("documentId");
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
		return id;}
		else {
			throw new NonRecoverableError();
		}
	}
	
	@Override
	public int getDocumentId() throws NonRecoverableError {
		if (documentId == 7) {
			System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}
		return documentId++;
	}
	
	

}
