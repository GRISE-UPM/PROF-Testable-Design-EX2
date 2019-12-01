package es.upm.grise.profundizacion.HandleDocuments;

/**
 * Fábrica de plantillas.
 */
public class TemplateFactory {

	/**
	 * Devuelve una plantilla válida para ser usada con {@link String#format(String, Object...)}.
	 * 
	 * @param templateName El nombre de la plantilla.
	 * @return La plantilla.
	 */
	public String getTemplate(String templateName) {
		String templateBody = null;
		switch (templateName) {
			case "DECLARATION":
				templateBody = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";
		}
		return templateBody;
	}

}
