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
        protected void getMySQLDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
            throw new ClassNotFoundException();
        }
        @Override
        public  DocumentIdProvider getInstance() throws NonRecoverableError {
            if (instance != null)
                return instance;
            else {
                instance = new ConnectionTest.DocumentIdProviderDouble();
                return instance;
            }
        }
    }

    @Test(expected = NonRecoverableError.class)
    public void MySQLDriverNotExist() throws NonRecoverableError, RecoverableError  {
        try{
            docIdProvider =  new ConnectionTest.DocumentIdProviderDouble().getInstance();
        }
        catch(NonRecoverableError e) {
            assertEquals(CANNOT_FIND_DRIVER.getMessage(), e.getMessage());
            throw e;
        }
    }
}
