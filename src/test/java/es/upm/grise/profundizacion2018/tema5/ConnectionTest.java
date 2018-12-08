package es.upm.grise.profundizacion2018.tema5;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_FIND_DRIVER;
import static org.junit.Assert.assertEquals;

public class ConnectionTest {
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
    public void MySQLDriverNotExist() throws NonRecoverableError, RecoverableError  {
        docIdProvider =  new DBTest.DocumentIdProviderDouble().getInstance();
        docIdProvider.CONNECTION_DRIVER = "CONNECTION DRIVER ERROR";
        try{
            docIdProvider.connectDB();
        }
        catch(NonRecoverableError e) {
            assertEquals(CANNOT_FIND_DRIVER.getMessage(), e.getMessage());
        }
    }
}
