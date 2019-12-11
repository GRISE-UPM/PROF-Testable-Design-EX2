package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderDoubleErrorRows extends DocumentIdProvider {
    private int documentId = 1;

    public DocumentIdProviderDoubleErrorRows( int docId ) throws NonRecoverableError {
        super();
        this.documentId = docId;
    }

    @Override
    protected int getLastObjectID() throws NonRecoverableError {
        throw new NonRecoverableError();
    }
}
