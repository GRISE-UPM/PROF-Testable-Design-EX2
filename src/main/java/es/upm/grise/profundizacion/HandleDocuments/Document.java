package es.upm.grise.profundizacion.HandleDocuments;

public class Document {

	// Document ID
	protected int documentId;
	
	// Document attributes
	protected String template;
	protected String author;
	protected String title;
	protected String body;
	protected TemplateFactory tf;
	
	public Document() {
		
	}
	
	public Document(String template, String author, String title, String body,  TemplateFactory templateFactory, DocumentIdProvider id) throws NonRecoverableError {
		this.template=template;
		this.author = author;
		this.title = title;
		this.body = body;
		this.documentId = id.getDocumentId();
		this.tf = templateFactory;
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
			
			System.out.println(Error.INCOMPLETE_DOCUMENT.getMessage());          	
			throw new RecoverableError();
			
		} else {

			return String.format(tf.getTemplate(template), documentId, title, author, body);
			
		}
	}

	public void setTf(TemplateFactory tf) {
		this.tf = tf;
	}

}
