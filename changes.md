# Changes made to the code

#DocumentIdProvider:

	## 1. "Environment variable" e "ID for the newly created document" variables cambiadas de private 		a protected
	
	## 2. DocumentIdProvider() cambiado de private a protected
	
	## 3. Esta clase ya no sera un Singleton
	
	## 4. Se ha separado la comprobacion de existencia de el archivo de configuracion en un nuevo 		metodo
	
	## 5. Se ha separado la carga del driver, la creacion de database, el cierre delas Db 		conexiones y algunas funciones mas en nuevos metodos

		
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
		
	## getDocumentId()valor de retorno cambiado de Object a int
	

#SmokeTest:	

	## Tests implementados y double de DocumentIdProvider