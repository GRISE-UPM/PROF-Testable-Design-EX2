package es.upm.grise.profundizacion.HandleDocuments;

public interface DocumentIdProvider {

	// Return the next valid objectID
	int getDocumentId() throws NonRecoverableError;

}