package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_CONNECT_DATABASE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_FIND_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_INSTANTIATE_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_READ_FILE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_RUN_QUERY;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CONNECTION_LOST;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.INCORRECT_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.NON_EXISTING_FILE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DocumentIdProviderDouble extends DocumentIdProvider {

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

	public DocumentIdProviderDouble() throws NonRecoverableError {
		// No hace falta llamar al super
	}

	@Override
	// Para poder testear la excepción de la última prueba simulando la actualización incorrecta
	public int getDocumentId() throws NonRecoverableError {
		if (documentId == 4) {
			System.out.println(CANNOT_UPDATE_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}
		return documentId++;
	}

	@Override
	public void loadPropertyFile(Properties properties, InputStream inputFile, String path) throws NonRecoverableError {
		super.loadPropertyFile(properties, inputFile, path);
	}

	@Override
	public void loadDBDriver(String driver) throws NonRecoverableError {
		super.loadDBDriver(driver);
	}

	@Override
	public void createDBConnection(String url, String username, String password) throws NonRecoverableError {

	}

	@Override
	public void readFromCountersTable() throws NonRecoverableError {
		//super.readFromCountersTable();
	}

	@Override
	public int getLastObjectID(ResultSet resultSet, Boolean real) throws NonRecoverableError {
		if(!real){
			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();
		}
		else return 1;
	}

	@Override
	public void closeAllDBConnections(ResultSet resultSet, Statement statement) throws NonRecoverableError {

	}

}
