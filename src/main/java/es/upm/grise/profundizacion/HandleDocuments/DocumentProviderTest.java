/**
 * 
 */
package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_CONNECT_DATABASE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_FIND_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_INSTANTIATE_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_READ_FILE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_RUN_QUERY;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
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

/**
 * @author raul
 *
 */
public class DocumentProviderTest extends DocumentIdProvider {

	/**
	 * @throws NonRecoverableError
	 */ 
	private static  DocumentProviderTest instance;
	int documentId = 0;
	public DocumentProviderTest() throws NonRecoverableError {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public static DocumentProviderTest getInstance() throws NonRecoverableError {
		if (instance != null) 

			return instance;

		else {

			instance = new DocumentProviderTest();
			return instance;

		}	
	}
	
	@Override
    protected String getPath() throws NonRecoverableError {
		return super.getPath();
		
	}
	@Override
	protected void loadPropertiesInFile(Properties propertiesInFile, InputStream inputFile, String path) throws NonRecoverableError {
		// Load the property file
		super.loadPropertiesInFile(propertiesInFile, inputFile, path);
}
	@Override
	protected void loadDBDriver(String driver) throws NonRecoverableError {
		// Load DB driver
		super.loadDBDriver(driver);
}
	@Override
	protected void createDBconnection(String url,String username,String password) throws NonRecoverableError {
		super.createDBconnection(url, username, password);
}
	@Override
	protected ResultSet executeQuery(Connection conection) throws NonRecoverableError {
		return super.executeQuery(conection);
}
	@Override
	protected DocumentIdProvider createConexion() throws NonRecoverableError {
		return super.createConexion(); 
    }
	protected int getlastObjectId(boolean prueba) throws NonRecoverableError{
		if(prueba)
		    return super.getlastObjectId();
		else
			System.out.println(CORRUPTED_COUNTER.getMessage());          	
		    throw new NonRecoverableError();
	}
	// Return the next valid objectID
	@Override
    public int getDocumentId() throws NonRecoverableError {

    	if (documentId == 4) {
			System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}
		return documentId++;

	}
}
