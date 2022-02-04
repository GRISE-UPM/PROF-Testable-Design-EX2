package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;

public class DocumentIdProviderDouble extends DocumentIdProvider{

    private int documentId;



    public DocumentIdProviderDouble() throws NonRecoverableError {
    }

    public DocumentIdProviderDouble(int documentId) throws NonRecoverableError {
        this.documentId = documentId;
    }

    @Override
    public int getDocumentId() throws NonRecoverableError {
        if(documentId < 0) {
            System.out.println(CORRUPTED_COUNTER.getMessage());
            throw new NonRecoverableError();
        }
        return documentId++;
    }


}
