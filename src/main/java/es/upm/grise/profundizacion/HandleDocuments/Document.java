package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.INCOMPLETE_DOCUMENT;

/**
 * Representa un documento.
 */
public class Document {

	/** La fábrica de plantillas. */
	private TemplateFactory templateFactory;
	
	/** El identificador del documento. */
	private int documentId;

	/** La plantilla del documento. */
	private String template;

	/** El autor del documento. */
	private String author;

	/** El título del documento. */
	private String title;

	/** El cuerpo del documento. */
	private String body;

	/**
	 * Crea un nuevo documento.
	 * 
	 * @param factory            La fábrica de plantillas.
	 * @param documentIdProvider El proveedor de identificadores para los documentos.
	 */
	public Document(TemplateFactory factory, DocumentIdProvider documentIdProvider) throws NonRecoverableError {
		templateFactory = factory;
		documentId = documentIdProvider.getDocumentId();
	}

	/**
	 * Asigna una plantilla.
	 * 
	 * @param template La plantilla.
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * Asigna un título.
	 * 
	 * @param title El título.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Asigna un autor.
	 * 
	 * @param autor El autor.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Asigna un cuerpo.
	 * 
	 * @param body El cuerpo.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Devuelve el identificador del documento.
	 * 
	 * @return El identificador.
	 */
	public Object getDocumentId() {
		return documentId;
	}

	/**
	 * Devuelve el documento como una cadena de texto formateada.
	 * 
	 * @return El documento formateado.
	 * @throws RecoverableError Si la plantilla, titulo, autor o cuerpo no están definidos.
	 */
	public String getFormattedDocument() throws RecoverableError {
		if (template == null || title == null || author == null || body == null) {
			System.err.println(INCOMPLETE_DOCUMENT.getMessage());
			throw new RecoverableError();
		} else {
			return String.format(templateFactory.getTemplate(template), documentId, title, author, body);	
		}
	}

}
