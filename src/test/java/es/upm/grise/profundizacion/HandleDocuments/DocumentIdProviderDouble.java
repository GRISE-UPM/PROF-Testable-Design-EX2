package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderDouble extends DocumentIdProvider{

	// ID for the newly created documents
	static int documentId;

	// Singleton access
	private static DocumentIdProviderDouble instance;

	public static DocumentIdProviderDouble getInstance() throws NonRecoverableError {
		if (instance != null)
			return instance;
		else {
			instance = new DocumentIdProviderDouble();
			//documentId = 0;
			return instance;
		}	
	}

	DocumentIdProviderDouble() { }

	// Return the next valid objectID
	public int getDocumentId() throws NonRecoverableError {
		return documentId++;
	}	
}
