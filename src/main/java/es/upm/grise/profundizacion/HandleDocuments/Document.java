package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.INCOMPLETE_DOCUMENT;

public class Document {

	private TemplateFactory templateFactory;
	
	// Document ID
	private int documentId;
	
	// Document attributes
	private String template;
	private String author;
	private String title;
	private String body;
	  
	public Document(DocumentIdProvider documentIdProvider, TemplateFactory templateFactory) throws NonRecoverableError {
		// NEW: insertar el singleton por constructor
		this.documentId = documentIdProvider.getDocumentId();
		// NEW: insertar templateFactory como instancia
		this.templateFactory = templateFactory;
		
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	// NEW: de Object a Int (no tener que castear en el test)
	public int getDocumentId() {
		return documentId;
	}
	
	public String getFormattedDocument() throws RecoverableError {
	
		// Check whether the document elements have been defined
		if (template == null || 
			title == null ||
			author == null ||
			body == null) {
			
			System.out.println(INCOMPLETE_DOCUMENT.getMessage());          	
			throw new RecoverableError();
			
		} else {

			return String.format(templateFactory.getTemplate(template), documentId, title, author, body);
			
		}
	}

}
