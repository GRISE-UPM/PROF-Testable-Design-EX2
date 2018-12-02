
 # Clase DocumentIdProvider

 - Para realizar las pruebas del punto 5, se han establecido los
   atributos String CONFIGFILE, DRIVE , UPDATE_QUERY y numOfValues que
   serán usados en vez de la introducción manual de las cadenas en el
   lugar de su ejecución.
   
  - Se ha cambiado la visibilidad de  documentId e instance a default
   para que puedan ser sobrecargados por las clases de test.
   
  - Por el mismo motivo se ha eliminado el modificador static del método
   getInstance().
   
  - Se ha quitado del constructor todo lo referente a la conexión con la
   base de datos y se crea pasado a nuevo método a sobreescribir
   connect(), que se lanza la primera vez que se crea una instancia.

# Clase Document

- Se ha añadido el atributo DocumentIdProvider _docProvider_  para poder establecer como DocumentIdProvider el doble que hemos creado.

- Para poder establecerlo, se ha creado además el método **public**  **void** cargar(DocumentIdProvider d) en el que también se recoge el documentId. De esta forma se separan la creación del Document y la obtención del ID, pudiendo así obtener el id proporcionado por el doble.

# Clase NonRecoverableError

- Se le han añadido los mensajes de error para poder diferenciar las excepciones durante los tests.
