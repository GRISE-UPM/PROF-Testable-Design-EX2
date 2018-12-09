package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import org.junit.Test;

import static es.upm.grise.profundizacion2018.tema5.Error.*;

public class DocumentIdProviderTestA {
	
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
		
	}
	
	@Test(expected = NonRecoverableError.class)
	// La aplicación detecta correctamente que el fichero de configuración no existe.
	public void configurationFileNotExist() throws NonRecoverableError, RecoverableError {
		try{
			provider = new DocumentIdProviderDouble().getInstance();
		}
		catch(NonRecoverableError e) {
			assertEquals(NON_EXISTING_FILE.getMessage(), e.getMessage());
			throw e;
		}
	}
	
}
