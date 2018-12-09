package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import static es.upm.grise.profundizacion2018.tema5.Error.*;

public class DocumentIdProviderTestD {

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
		protected void getMySQLDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException {	}
		
		@Override
		protected void getConnection(String url, String username, String password) throws SQLException { }
		
		@Override
		protected void executeQuery(String query) throws SQLException {	}
		
		@Override
		protected void getDocumentIdFromDB() throws SQLException {
			numberOfValues++;
		}
		
		@Override
		protected void closeDBConnections() throws SQLException { }
		
		@Override
		protected int updateCounter() throws SQLException {
			return 0;
		}
		
	}
	
	@Test(expected = NonRecoverableError.class)
	// La aplicación detecta correctamente que la actualización del	documentID en la tabla Counters ha sido incorrectamente	realizado
    public void documentIdUpdatedIncorrect() throws NonRecoverableError {
		provider = new DocumentIdProviderDouble().getInstance();
        provider.connectToDB();
        try{
            provider.getDocumentId();
        } catch (NonRecoverableError e) {
            assertEquals(CORRUPTED_COUNTER.getMessage(), e.getMessage());
            throw e;
        }
    }
}
