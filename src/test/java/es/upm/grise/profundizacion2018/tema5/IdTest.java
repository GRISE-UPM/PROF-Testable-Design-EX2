package es.upm.grise.profundizacion2018.tema5;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_UPDATE_COUNTER;
import static es.upm.grise.profundizacion2018.tema5.Error.CORRUPTED_COUNTER;
import static org.junit.Assert.assertEquals;

public class IdTest {
    protected static DocumentIdProvider docIdProvider;
    public static class DocumentIdProviderDouble extends DocumentIdProvider {
        protected DocumentIdProviderDouble() throws NonRecoverableError {
            super();
        }

        @Override
        protected String getEnvironment() {
            return "/";
        }
        @Override
        protected void getFile(String path) throws FileNotFoundException, IOException {}
        @Override
        protected void getMySQLDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException {	}
        @Override
        protected void getConnection(String url, String username, String password) throws SQLException { }
        @Override
        protected void executeQuery(String query) throws SQLException {	}
        @Override
        protected void getDocumentIdFromDB() throws SQLException {
            numberOfvalues++;
        }
        @Override
        protected void closeDBConnections() throws SQLException { }
        @Override
        protected int updateCounter() throws SQLException {
            return 0;
        }
        @Override
        public  DocumentIdProvider getInstance() throws NonRecoverableError {
            if (instance != null)
                return instance;
            else {
                instance = new IdTest.DocumentIdProviderDouble();
                return instance;
            }
        }
    }

    @Test(expected = NonRecoverableError.class)
    public void documentIdUpdatedIncorrect() throws NonRecoverableError {
        docIdProvider =  new IdTest.DocumentIdProviderDouble().getInstance();
        docIdProvider.connectDB();
        try{
            docIdProvider.getDocumentId();
        }
        catch (NonRecoverableError e) {
            assertEquals(CORRUPTED_COUNTER.getMessage(), e.getMessage());
            throw e;
        }
    }
}
