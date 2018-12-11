package es.upm.grise.profundizacion2018.tema5;

import static es.upm.grise.profundizacion2018.tema5.Error.INCOMPLETE_DOCUMENT;

public class Document {

	// Document ID
	protected int documentId;
	
	// Document attributes
	protected String template;
	protected String author;
	protected String title;
	protected String body;
	
//	public Document() throws NonRecoverableError {
//		this.documentId = DocumentIdProvider.getInstance().getDocumentId();
//	}

	public Document() {
		super();
	}
	
	public Document(String template, String author, String title, String body) throws NonRecoverableError {
		this.template = template;
		this.author = author;
		this.title = title;
		this.body = body;
		DocumentIdProvider docProv = new DocumentIdProvider();
		this.documentId = docProv.getDocumentId();
	}
	
//	public Document(String template, String author, String title, String body, int documentId){
//		this.template = template;
//		this.author = author;
//		this.title = title;
//		this.body = body;
//		this.documentId = documentId;
//	}
	
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
	// Añadido setter para la Id del documento
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
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
