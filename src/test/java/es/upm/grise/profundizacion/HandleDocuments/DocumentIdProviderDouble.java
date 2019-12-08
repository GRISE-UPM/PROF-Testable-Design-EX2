package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderDouble extends DocumentIdProvider {
	
	private int documentId = 1;

	public DocumentIdProviderDouble(int id) throws NonRecoverableError{
		super(); 
		documentId = id;
		}

	@Override
	public int getDocumentId() throws NonRecoverableError {
		if(documentId >= 0)
			return documentId++;
		else       	
			throw new NonRecoverableError();
	}
	
}
