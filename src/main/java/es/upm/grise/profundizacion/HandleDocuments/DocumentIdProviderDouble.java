package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_FIND_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_INSTANTIATE_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_READ_FILE;
import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.NON_EXISTING_FILE;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DocumentIdProviderDouble extends DocumentIdProvider {

	private static DocumentIdProviderDouble instance;
	private int documentId=1;
	
	public static DocumentIdProviderDouble getInstance() throws NonRecoverableError {
		if (instance != null)

			return instance;

		else {

			instance = new DocumentIdProviderDouble();
			return instance;

		}
	}
	public DocumentIdProviderDouble() throws NonRecoverableError {
		// TODO Auto-generated constructor stub
	
	}
	
	@Override
	public int getDocumentId()  {
		return documentId;
	}

	@Override
	protected void cargarDriver(String driver) throws NonRecoverableError {
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
	
	@Override
	protected void ficheroProperties(String path, String fichero) throws NonRecoverableError {
		try {
			InputStream inputFile = null;
			inputFile = new FileInputStream(path + fichero);
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
	protected void checkearID(int valor) throws NonRecoverableError {
		
		if(valor != 1) {

			System.out.println(CORRUPTED_COUNTER.getMessage());          	
			throw new NonRecoverableError();

		}
	}
	
	

}
