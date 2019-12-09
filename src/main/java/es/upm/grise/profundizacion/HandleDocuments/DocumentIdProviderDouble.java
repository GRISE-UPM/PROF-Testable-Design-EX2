package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.NON_EXISTING_FILE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_READ_FILE;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class DocumentIdProviderDouble extends DocumentIdProvider{

	public DocumentIdProviderDouble() throws NonRecoverableError {
		super();
		// TODO Auto-generated constructor stub
	}
	
	int documentId = 1;

	// Singleton access
	static DocumentIdProviderDouble instance;

	public static DocumentIdProviderDouble getInstance() throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {

			instance = new DocumentIdProviderDouble();
			return instance;

		}
	}
	
	@Override
	public String getPath() throws NonRecoverableError{

		return super.getPath();
	}
	
	@Override
	public void getProperties(Properties properties, InputStream inputFile) throws NonRecoverableError{
		String path = "test";

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
	public void loadDriver(String driver) throws NonRecoverableError{

		 super.loadDriver(driver);
	}
	
	@Override
	public void createConnection(String url, String username, String password) throws NonRecoverableError{
		super.createConnection(url, username, password);
	}
	
	@Override
	public ResultSet executeQuery(Connection connection) throws NonRecoverableError {
		
		return super.executeQuery(connection);
	}
	
	@Override
	public DocumentIdProvider connect() throws NonRecoverableError {
		return super.connect();
	}
	
	@Override
	public int getlastObjectId() throws NonRecoverableError{
		
		return 1;
	}
	
	@Override
	public int getDocumentId() throws NonRecoverableError {
		if (documentId == 6) {
			System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}
		return documentId++;
	}

}
