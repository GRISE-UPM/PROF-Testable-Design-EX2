# Changes made to the code


1. Añadimos un constructor a Document para el testeo más efectivo.
   1. formatTemplateCorrectly pasa el test
2. Cambiamos la visibilidad de la funcion DocumentIdProvider a public
3. Cambiamos int documentId a protected
4. En documentId provider cambiamos los parametros de entrada
5. En documentIdProvider.getInstance refactorizo el codigo para que tenga menos lineas.
6. Añadimos a DocumentId provider un getter para documentId (getInstanceDocumentId) 
7. Añadimos el parametro documentlastid a getdocumentid
