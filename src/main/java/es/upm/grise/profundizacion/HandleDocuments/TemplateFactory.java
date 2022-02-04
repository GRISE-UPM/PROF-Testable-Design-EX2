package es.upm.grise.profundizacion.HandleDocuments;

public interface TemplateFactory {

	// Returns a template that could be processed String.format()
	String getTemplate(String templateName);

}