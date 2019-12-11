package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderDouble extends DocumentIdProvider {
    private int documentId = 1;

    public DocumentIdProviderDouble( int docId ) throws NonRecoverableError {
        super();
        this.documentId = docId;
    }

    @Override
    public int getDocumentId() throws NonRecoverableError {
        if( documentId > 0 ) {
            return this.documentId;
        }
        else {
            throw new NonRecoverableError();
        }
    }
}
