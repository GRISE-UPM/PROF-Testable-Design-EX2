# Changes made to the code
- Se crea el constructor Document(int documentId) para asignar directamente el valor idDocument
 
- Se modifican las clases RecoverableError y NonRecoverableError para devolver el mensaje de error asociado a cada excpeción
 
- Se modifica el contructor de DocumentIdProvider para que pueda asignase el path y el driver de la BBDD
- Se crea un contructor de DocumentIdProvider para asignar directamente el valor idDocument sin usar la BBDD
- Se cambia a public los contructores de DocumentIdProvider
- Se cambia la clase del driver de mysql, ya que el actual está deprecado
- Se modifica la variable path para que use la misma ruta donde está el fichero config.properties
 
- Se crea la clase DocumentProviderInt como una clase extendida de DocumentProvider. 
Se implementan los métodos mínimos necesarios para realizar los tests de los apartados 4 y 5: 
los constructores DocumentProviderInt y se redefine getDocumentId()

- Se implementan los tests de los apartados 4 y 5 en SmokeTest
