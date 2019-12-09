package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

public class DocumentIDInstances extends DocumentIdProvider{

	
	int documentId = 1;

	// Singleton access
	static DocumentIDInstances instance;

	public static DocumentIDInstances getInstance() throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {

			instance = new DocumentIDInstances();
			return instance;

		}
	}

	public DocumentIDInstances() throws NonRecoverableError {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String getPath() throws NonRecoverableError{

		return super.getPath();
	}
	
	@Override
	public void getProperties(Properties properties, InputStream inputFile, String path) throws NonRecoverableError{
		super.getProperties(properties, inputFile, path);
	}
	
	@Override
	public void loadDrivers(String driver) throws NonRecoverableError{

		 super.loadDrivers(driver);
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
	public DocumentIdProvider connnect() throws NonRecoverableError {
		return super.connnect();
	}
	
	@Override
	public int getID(boolean conection) throws NonRecoverableError{
		if(!conection){
			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}
		return 1;
	}
	
	@Override
	public int getDocumentId() throws NonRecoverableError {
		if (documentId == 5) {
			System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}
		return documentId++;
	}

}
