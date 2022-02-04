package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderFake implements DocumentIdProvider {

	@Override
	public int getDocumentId() throws NonRecoverableError {
		return 1;
	}

}
