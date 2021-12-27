# Changes made to the code

1. Se crea un constructor para Document que nos permita ponerle el Id que nosotros queramos y abstraernos del uso de otras clases.
2. En getDocumentId(int lastId) se le pasa por parámetro el Id para facilitar las pruebas. Además se crea un getter nuevo para esa la variable de DocumentId.
3. Se cambia la visibilidad en public DocumentIdProvider() para poder invocarlo y comprobar que detecta correctamente el fichero de configuración.
4. A nonRecoverableError se le añade un campo type para comprobar qué tipo de excepciones se lanzan y testar correctamente todo.
5. public DocumentIdProvider(String path) se le pasa el path por parámetro para poder testar que no existe un fichero.
6. Se le añade String DBDriver como parámetro para poder realizar el test pertinente
7. Se cambia a DocumentIdProvider(String path, String DBDriver, String DBURL, String DBUSER, String DBPASS), para poder apuntar a la BD que nosotros queramos y testar ciertas cosas.

