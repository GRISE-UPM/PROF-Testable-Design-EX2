package es.upm.grise.profundizacion.HandleDocuments;

/**
 * La aplicación que maneja los documentos.
 */
public class HandleDocuments {

	/**
	 * El punto de entrada principal.
	 * 
	 * @param args Los argumentos.
	 */
	public static void main(String[] args) {
		String TEMPLATE = args[1].toUpperCase();
		String TITLE = args[3];
		String AUTHOR = args[5];
		String BODY = args[7];
		try {
			Document document = new Document(new TemplateFactory(), DocumentIdProvider.getInstance());
			document.setTemplate(TEMPLATE);
			document.setAuthor(AUTHOR);
			document.setTitle(TITLE);
			document.setBody(BODY);
			System.out.println(document.getFormattedDocument());
			System.exit(0);	
		} catch (RecoverableError e) {
			System.exit(1);
		} catch (NonRecoverableError e) {
			System.exit(1);
		}
	}

}
