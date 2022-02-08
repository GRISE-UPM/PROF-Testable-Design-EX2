package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DocumentIdProviderInt extends DocumentIdProvider {

        public DocumentIdProviderInt(String path, String driverDB) throws NonRecoverableError {
            super(path, driverDB);
        }

        public DocumentIdProviderInt(int documentId) throws NonRecoverableError {
            super(documentId);
        }

        // Return the next valid objectID
        @Override
        public int getDocumentId() throws NonRecoverableError {
            if(documentId == 0) {
                System.out.println(CORRUPTED_COUNTER.getMessage());
                throw new NonRecoverableError(CORRUPTED_COUNTER.getMessage());

            } else if (documentId < 0) {
                System.out.println(INCORRECT_COUNTER.getMessage());
                throw new NonRecoverableError(INCORRECT_COUNTER.getMessage());
            }
            this.documentId++;
            return documentId;
        }

}
