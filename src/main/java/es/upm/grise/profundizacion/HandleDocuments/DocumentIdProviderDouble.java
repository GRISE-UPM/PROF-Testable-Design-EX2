package es.upm.grise.profundizacion.HandleDocuments;

import java.sql.Connection;

public class DocumentIdProviderDouble extends DocumentIdProvider {

    // ID for the newly created documents
    int documentId;
    // Singleton access
    static DocumentIdProviderDouble instance;

    DocumentIdProviderDouble(int id) {
        super(id);
    }


    @Override
    public int getDocumentId() throws NonRecoverableError {
        super.documentId ++;
        return super.documentId;
    }




}
