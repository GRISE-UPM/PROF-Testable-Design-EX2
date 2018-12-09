package es.upm.grise.profundizacion2018.tema5;

import org.junit.Test;

import static es.upm.grise.profundizacion2018.tema5.Error.*;
import static org.junit.Assert.assertEquals;

public class FileTest {
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
        public  DocumentIdProvider getInstance() throws NonRecoverableError {
            if (instance != null)
                return instance;
            else {
                instance = new FileTest.DocumentIdProviderDouble();
                return instance;
            }
        }
    }

    @Test(expected = NonRecoverableError.class)
    public void configurationFileNotExist() throws NonRecoverableError, RecoverableError {
        try {
            docIdProvider =  new FileTest.DocumentIdProviderDouble().getInstance();
        }
        catch(NonRecoverableError e) {
            assertEquals(NON_EXISTING_FILE.getMessage(), e.getMessage());
            throw e;
        }
    }
}
