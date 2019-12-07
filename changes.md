# Changes made to the code

Clase Document:
- El DocumentIdProvider se inyecta por constructor.
- La TemplateFactory se inyecta por constructor (no era necesario para los tests pero prefería inyectarla para evitar tener colaboradores hardcodeados)
- Modificado el tipo de retorno del método getDocumentId de Object a int.
- La excepción del método getFormattedDocument ahora tiene un mensaje para especificar el fallo.
- Arreglos en indentación.

Clase DocumentIdProvider:
- El constructor ahora es protected para permitir que los test double lo usen.
- Se ha troceado el constructor para disponer de diferentes métodos que realicen una función concreta.
- Se exponen (protected) solo los métodos necesarios para que el test double pueda disponer de ellos para los tests el resto son private. En concreto se exponen los siguientes:
  * __protected Properties loadProperties(String path) throws NonRecoverableError__. Este método retorna el fichero de properties si lo encuentra en el path o una excepción si no lo encuentra o no puede leerlo. Necesario para el test de detección de que el fichero de configuración no existe.
  * __protected void loadDatabaseDriver(String driverClass) throws NonRecoverableError__. Este método carga el driver de base de datos especificado, en caso de no poder lanza una excepción. Necesario para el test de detección de que el driver de MySQL no existe. Se han reordenado las sentencias catch para ser consistentes con el funcionamiento de Reflection, primero búsqueda de clase, luego comprobación de acceso y por ultimo instanciación.
  * __protected int gestLastId(ResultSet resultSet) throws NonRecoverableError__. Este método retorna el ultimo id que existe en la base de datos o un error si el contador es incorrecto o se ha corrompido el id. Necesario para el tests de detección de que hay una fila más en la tabla Counters.
- Las excepciones ahora tienen mensaje para desambiguar las causas.
- La conexión ahora es privada ya que no era necesario que fuese package private.
- Los imports se han especificado.
- Mejoras en indentación.

Clase TemplateFactory:
- Modificado el método getTemplate para que ya no sea estático.
- Añadido método estático de construcción para que sea un Singleton.
- Mejoras en la indentación.

Clase Error:
- Mejoras en indentación.

Clase HandleDocumentsMainClass:
- Modificada la creación del documento debido a los cambios en su constructor.
- Mejora en indentación.

Clase NonRecoverableError:
- Añadido un mensaje para desambiguar la causa de la excepción.

Clase RecoverableError:
- Añadido un mensaje para desambiguar la causa de la excepción.

Clase DocumentIdProviderDouble:
- Test double para los tests realizados. Hereda de DocumentIdProvider e implementa los métodos mencionados anteriormente.

Clase AbstractResultSetDouble:
- Clase abstracta que permite que la clase que se utilizara como test double de ResultSet solo tenga que implementar el método next.

Clase ResultSetDouble:
- Test double para los tests realizados. Hereda de AbstractResultSetDouble. Implementa el método next.

Clase SmokeTest:
- Contiene los tests desarrollados y métodos privados para mejorar la legibilidad.
