package es.upm.grise.profundizacion2018.tema5;

import static es.upm.grise.profundizacion2018.tema5.Error.INCOMPLETE_DOCUMENT;

public class Document {

	// Document ID
	private int documentId;
	
	// Document attributes
	private String template;
	private String author;
	private String title;
	private String body;


	//Para que esta clase sea completamente testable hace falta que el proveedor de ids sea inuectado

	public Document(DocumentIdProvider documentIdProvider, String template, String author, String title, String body) throws NonRecoverableError{
		this.documentId = documentIdProvider.getDocumentId();
		this.template = template;
		this.author = author;
		this.title = title;
		this.body = body;
	}

	public Object getDocumentId() {
		return documentId;
	}
	
	public String getFormattedDocument() throws RecoverableError {
	
		// Check whether the document elements have been defined
		if (template == null || 
			title == null ||
			author == null ||
			body == null) {
			throw new RecoverableError(INCOMPLETE_DOCUMENT.getMessage());
			
		} else {

			return String.format(TemplateFactory.getTemplate(template), documentId, title, author, body);
			
		}
	}

}
