package es.upm.grise.profundizacion2018.tema5;

import static es.upm.grise.profundizacion2018.tema5.Error.INCOMPLETE_DOCUMENT;

public class Document {

	// Document ID
	public int documentId;
	public DocumentIdProvider documentIdProvider;
	// Document attributes
	public String template;
	public String author;
	public String title;
	public String body;
	
	public Document() throws NonRecoverableError {
		documentIdProvider = new DocumentIdProvider();
		this.documentId = documentIdProvider.getDocumentId();
	}
	public Document(int documentId) throws NonRecoverableError {
		
		this.documentId = documentId;
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

			return String.format(TemplateFactory.getTemplate(template), documentId, title, author, body);
			
		}
	}

}
