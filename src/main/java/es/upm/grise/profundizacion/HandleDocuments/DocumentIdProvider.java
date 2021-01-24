package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProvider {
    private final MySQLHelper mySQLHelper;

    public DocumentIdProvider(MySQLHelper mySQLHelper) {
        this.mySQLHelper = mySQLHelper;
    }

    // Return the next valid objectID
    public int getDocumentId() throws NonRecoverableError {
        final int documentId = this.mySQLHelper.getLastDocumentId() + 1;
        this.mySQLHelper.updateLastDocumentId(documentId);
        return documentId;
    }
}
