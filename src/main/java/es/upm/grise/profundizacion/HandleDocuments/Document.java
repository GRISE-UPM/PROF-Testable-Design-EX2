package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.INCOMPLETE_DOCUMENT;

public class Document {

	// Document ID
	protected int documentId;
	
	private DocumentIdProvider docIdprovider;
	
	// Document attributes
	private String template;
	private String author;
	private String title;
	private String body;
	
	public Document() throws NonRecoverableError {
		this.documentId = docIdprovider.getInstance().getDocumentId(docIdprovider.getInstance().getInstanceDocumentId());
	}
	
	public Document(int documentId) {
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
	
	public Object getDocumentId() {
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
			TemplateFactory templateFactory = new TemplateFactory();
			return String.format(templateFactory.getTemplate(template), documentId, title, author, body);
		}
	}

}
