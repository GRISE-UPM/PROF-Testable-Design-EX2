package es.upm.grise.profundizacion.HandleDocuments;

public class DocumentIdProviderDouble extends DocumentIdProvider {

	int documentId=3;
	
	// Singleton access
		static DocumentIdProviderDouble instance;
		protected boolean error=false;

		public static DocumentIdProviderDouble getInstance() throws NonRecoverableError {
			if (instance != null)

				return instance;

			else {

				instance = new DocumentIdProviderDouble();
				return instance;

			}
		}
		
		protected DocumentIdProviderDouble() throws NonRecoverableError {
			//super();
			// TODO Auto-generated constructor stub
		}

	
	

	
	@Override
	public int getDocumentId() throws NonRecoverableError{
		if(error==true){
			throw new NonRecoverableError();
		}
		return documentId++;
		
	}
	
	//metodo que va a devolver error la siguiente vez
	protected void laSiguienteDaError() {
		error=true;
	}
	
	

}
