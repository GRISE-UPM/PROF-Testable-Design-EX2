package es.upm.grise.profundizacion2018.tema5;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static es.upm.grise.profundizacion2018.tema5.Error.*;
import static org.junit.Assert.assertEquals;

public class DBTest {
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
        public  DocumentIdProvider getInstance() throws NonRecoverableError {
            if (instance != null)
                return instance;
            else {
                instance = new DBTest.DocumentIdProviderDouble();
                instance.connectDB();
                return instance;
            }
        }
    }

    @Test(expected = NonRecoverableError.class)
    public void oneMoreRowInCounterTable() throws NonRecoverableError {
        docIdProvider =  new DBTest.DocumentIdProviderDouble().getInstance();
        docIdProvider.numberOfvalues = 1;
        try{
            docIdProvider.connectDB();
        }
        catch(NonRecoverableError e) {
            assertEquals(CORRUPTED_COUNTER.getMessage(), e.getMessage());
        }
    }

    @Test(expected = NonRecoverableError.class)
    public void documentIdUpdatedIncorrect() throws NonRecoverableError {
        docIdProvider =  new DBTest.DocumentIdProviderDouble().getInstance();
        docIdProvider.QUERY = "UPDATE Counters SET = ?";
        docIdProvider.connectDB();
        try{
            docIdProvider.getDocumentId();
        }
        catch (NonRecoverableError e) {
            assertEquals(CANNOT_UPDATE_COUNTER.getMessage(), e.getMessage());
        }
    }
}
