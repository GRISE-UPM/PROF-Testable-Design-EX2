package es.upm.grise.profundizacion.HandleDocuments;

import java.sql.Connection;

public class DocumentIdProviderDouble extends DocumentIdProvider {
    DocumentIdProviderDouble() throws NonRecoverableError {
    }

    DocumentIdProviderDouble(Connection connection) throws NonRecoverableError {
        this.connection = connection;
        this.documentId = 0;
    }
}
