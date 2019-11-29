package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentDouble extends Document{
	
	// Crea nuestro objeto para pruebas con el ID generado por DocumentIdProviderDouble
	public DocumentDouble() {
		try {
			this.setDocumentId(DocumentIdProviderDouble.getInstance().getDocumentId());
		} catch (NonRecoverableError e) { }
	}
}
