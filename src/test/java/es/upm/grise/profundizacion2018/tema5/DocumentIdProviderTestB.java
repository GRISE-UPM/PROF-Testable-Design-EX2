package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import static es.upm.grise.profundizacion2018.tema5.Error.*;

public class DocumentIdProviderTestB {

	DocumentIdProvider provider;
	
	public static class DocumentIdProviderDouble extends DocumentIdProvider {
		
		@Override
		public DocumentIdProvider getInstance() throws NonRecoverableError {
			if (instance != null)

				return instance;

			else {

				instance = new DocumentIdProviderDouble();
				return instance;

			}	
		}
		
		protected DocumentIdProviderDouble() throws NonRecoverableError {
			super();
		}
		
		protected String getEnvironment() {
			return "/";
		}
		
		protected void getFile(String path) throws FileNotFoundException,IOException { }
		
		@Override
		protected void getMySQLDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			throw new ClassNotFoundException();
		}
		
	}
	
	@Test(expected = NonRecoverableError.class)
	// La aplicación detecta correctamente que el driver MySQL no existe
	public void MySQLDriverNotExist() throws NonRecoverableError, RecoverableError {
		try{
			provider = new DocumentIdProviderDouble().getInstance();
		}
		catch(NonRecoverableError e) {
			assertEquals(CANNOT_FIND_DRIVER.getMessage(), e.getMessage());
			throw e;
		}
	}
}
