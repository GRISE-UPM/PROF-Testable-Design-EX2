# Changes made to the code
Clases modificadas para un correcto diseño testable:
## Clase Document: 
Para la asignación del documentID se ha implementado un metodo en vez de hacerlo en el constructor para permitir diseño testable.
## Clase DocumentIdProvider
Para la lectura del fichero de properties y para la conexión de la base de datos se han utilizado dos funciones auxiliares en vez de en la misma. (readProp y readFromDB) 
## Clase HandleDocuments
Asignación del driver con el metodo creado en la clase Document (setDocId)
document.setDocId(DocumentIdProvider.getInstance(DocumentIdProvider.DRIVER));
## Clase NOnRecoverableError
Se han añadido los dos constructores (uno vacio y uno recibiendo el mensaje)
