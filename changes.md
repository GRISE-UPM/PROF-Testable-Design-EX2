# Changes made to the code

#DocumentIdProvider:

	## 1. "Environment variable" e "ID for the newly created document" variables cambiadas de private a   	protected
	
	## 2. DocumentIdProvider() cambiado de private a protected
	
	## 3. Esta clase ya no sera un Singleton
	
#Document:

	## Todas las variables privadas cambiadas por protected
	
	## Como DocumentIdProvider ya no es Singleton se ha realizado el siguiente cambio: 
	
	   this.documentId = DocumentIdProvider.getInstance().getDocumentId();
	   							|
	   							|
	   							|
	   							|
	   							V
		documentIdProvider = new DocumentIdProvider();
		this.documentId = documentIdProvider.getDocumentId();
		
	## getDocumentId()valor de retorno cmabiado de Object a int
	
#TemplateFactory:

	##