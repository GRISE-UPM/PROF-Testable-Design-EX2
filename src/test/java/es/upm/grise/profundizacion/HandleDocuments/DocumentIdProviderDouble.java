package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CORRUPTED_COUNTER;

public class DocumentIdProviderDouble extends DocumentIdProvider {

	public int documentId;
	
	public DocumentIdProviderDouble(int documentId) throws NonRecoverableError {
		super();
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

	@Override
	public void getLastId() throws NonRecoverableError {
		throw new NonRecoverableError();
	}
	
	@Override
	public String getPath() {
		return "";
	}
	
}
