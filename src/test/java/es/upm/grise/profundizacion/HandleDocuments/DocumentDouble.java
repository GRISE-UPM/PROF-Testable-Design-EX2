package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentDouble extends Document{

	public DocumentDouble() throws NonRecoverableError {
		this.documentId = DocumentIdProviderDouble.getInstance().getDocumentId();
	}
	

}
