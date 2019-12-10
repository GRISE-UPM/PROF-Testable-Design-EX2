package es.upm.grise.profundizacion.HandleDocuments;

public class TemplateFactory {

	private static TemplateFactory instance;

	// NEW: singleton, ahora que getTemplate no es estatico.
	public static TemplateFactory getInstance(){
		if (instance != null)

			return instance;

		else {

			instance = new TemplateFactory();
			return instance;

		}	
	}
	
	// NEW: ya no es estatico
	// Returns a template that could be processed String.format()
	public String getTemplate(String templateName) {
		
		String templateBody = null;

		switch(templateName) {
		
			case "DECLARATION" : templateBody = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";
		
		}
		
		return templateBody;
	}	

}