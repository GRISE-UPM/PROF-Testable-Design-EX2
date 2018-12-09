package es.upm.grise.profundizacion2018.tema5;

public class HandleDocuments {

	public static void main(String[] args) {
		
		// Previous code does not matter .... 
		// You can assume in particular that parameters have been checked
		
		// The document elements
		String TEMPLATE = args[0].toUpperCase();
		String TITLE = args[1];
		String AUTHOR = args[2];
		String BODY = args[3];
		
		try {
			
			Document document = new Document(new DocumentIdProvider(new EnvironmentHandler(), new ConfigurationHandler(), null), TEMPLATE, AUTHOR, TITLE, BODY);
			/*
			document.setTemplate(TEMPLATE);
			document.setAuthor(AUTHOR);
			document.setTitle(TITLE);
			document.setBody(BODY);
			System.out.println(TEMPLATE);
			System.out.println(AUTHOR);
			System.out.println(TITLE);
			System.out.println(BODY);
			*/
			System.out.println(document.getFormattedDocument());
			
			// What follows does not matter either ....
			
			// Exit without error
			System.exit(0);
			
		} catch (RecoverableError e) {
			
			// Exit with error
			System.exit(1);
			
		} catch (NonRecoverableError e) {
			
			// Exit with error
			System.exit(1);
		
		}
		

	}

}
