package es.upm.grise.profundizacion2018.tema5;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static es.upm.grise.profundizacion2018.tema5.Error.*;
import static org.junit.Assert.assertEquals;

public class RowTest {
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
            numberOfvalues = numberOfvalues + 5;
        }
        public  DocumentIdProvider getInstance() throws NonRecoverableError {
            if (instance != null)
                return instance;
            else {
                instance = new RowTest.DocumentIdProviderDouble();
                return instance;
            }
        }
    }

    @Test(expected = NonRecoverableError.class)
    public void oneMoreRowInCounterTable() throws NonRecoverableError {
        try{
            docIdProvider = new RowTest.DocumentIdProviderDouble().getInstance();
        }
        catch(NonRecoverableError e) {
            assertEquals(CORRUPTED_COUNTER.getMessage(), e.getMessage());
            throw e;
        }
    }
}
