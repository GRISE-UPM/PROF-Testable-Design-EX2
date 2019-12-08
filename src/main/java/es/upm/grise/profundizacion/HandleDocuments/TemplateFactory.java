package es.upm.grise.profundizacion.HandleDocuments;

public class TemplateFactory {

	// Returns a template that could be processed String.format()
	public static String getTemplate(String templateName) {
		
		String templateBody = null;

		switch(templateName) {
		
			case "DECLARATION" : templateBody = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";
		
		}
		
		return templateBody;
	}	

}
