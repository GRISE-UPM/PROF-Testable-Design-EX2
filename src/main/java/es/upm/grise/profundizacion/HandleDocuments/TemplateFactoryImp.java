package es.upm.grise.profundizacion.HandleDocuments;

public class TemplateFactoryImp implements TemplateFactory{

	// Returns a template that could be processed String.format()
	@Override
	public String getTemplate(String templateName) {
		
		String templateBody = null;

		switch(templateName) {
		
			case "DECLARATION" : templateBody = "DOCUMENT ID: %d\n\nTitle : %s\nAuthor: %s\n\n%s";
		
		}
		
		return templateBody;
	}	

}
