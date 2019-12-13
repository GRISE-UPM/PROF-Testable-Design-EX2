package es.upm.grise.profundizacion.HandleDocuments;

public class HandleDocumentsMainClass {

	public static void main(String[] args) {
		
		// Previous code does not matter .... 
		// You can assume in particular that parameters have been checked
		
		// The document elements
		String TEMPLATE = args[1].toUpperCase();
		String TITLE = args[3];
		String AUTHOR = args[5];
		String BODY = args[7];
		
		try {
			
			Document document = new Document();
			document.setTemplate(TEMPLATE);
			document.setAuthor(AUTHOR);
			document.setTitle(TITLE);
			document.setBody(BODY);
			System.out.println(document.getFormattedDocument());
			
			// What follows does not matter either ....
			
			// Exit without error
			System.exit(0);
			
		} catch (RecoverableError e) {
			
			// Exit with error
			System.exit(1);
			
		}
		

	}

}
