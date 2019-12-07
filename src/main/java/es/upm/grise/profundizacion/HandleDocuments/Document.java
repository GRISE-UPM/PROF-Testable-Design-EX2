package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.INCOMPLETE_DOCUMENT;

public class Document {

	// Document ID
	private int documentId;

	// Document attributes
	private String template;
	private String author;
	private String title;
	private String body;

	private TemplateFactory templateFactory;

	public Document(DocumentIdProvider documentIdProvider, TemplateFactory templateFactory) throws NonRecoverableError {
		this.documentId = documentIdProvider.getDocumentId();
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

	public int getDocumentId() {
		return documentId;
	}

	public String getFormattedDocument() throws RecoverableError {

		// Check whether the document elements have been defined
		if (template == null || 
				title == null ||
				author == null ||
				body == null) {

			String message = INCOMPLETE_DOCUMENT.getMessage();
			System.out.println(message);          	
			throw new RecoverableError(message);

		} else {

			return String.format(templateFactory.getTemplate(template), documentId, title, author, body);
		}
	}

}
