package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentFactory {
    private final DocumentIdProvider documentIdProvider;

    public DocumentFactory(DocumentIdProvider documentIdProvider) {
        this.documentIdProvider = documentIdProvider;
    }

    public Document createDocument() throws NonRecoverableError {
        return new Document(documentIdProvider.getDocumentId());
    }
}
