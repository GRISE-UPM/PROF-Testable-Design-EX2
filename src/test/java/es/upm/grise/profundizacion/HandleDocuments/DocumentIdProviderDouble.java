package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderDouble extends DocumentIdProvider {

	public DocumentIdProviderDouble() throws NonRecoverableError {
		
	}
	
	public static DocumentIdProvider getInstance() throws NonRecoverableError {
		if (instance != null)
			return instance;

		else {
			instance = new DocumentIdProviderDouble();
			return instance;

		}	
	}
	
	@Override
	public int getDocumentId() throws NonRecoverableError {
		return 1623;
	}

}
