# Changes made to the code

##### Guillermo Carrera Trasobares

#### Changelog
1. Debido a que la variable de entorno APP_HOME no está definida, se incluyen las dependencias de powermockito para moquear la clase estática System de java cuando se realizan los tests, de forma que se simplifique el diseño de los mismos cuando se quiere acceder acceder al fichero de configuración.
2. Nuevo método `public void setDocumentId(int documentId)` en Documento
3. Tipo de retorno de getDocumentId() cambiado a int (desde Object)
4. Nuevo constructor `Document(String template, String author, String title, String body)` para generar un nuevo objeto y asignarle un documentId de forma automática. Normalmente se intenta limitar la lógica de los constructores, pero en este caso se justifica al ser el programa tan simple. Se mantiene el superconstructor y se ha añadido otro constructor (comentado) al que se le pueden pasar todos los parámetros incluyendo el documentId.
5. Eliminar el Singleton de `DocumentIdProvider`.
6. En `HandleDocuments` lanzar `NoRecoverableError` con un `throws`.
7. En DocumentIdProviders crear funciones independientes para:
	- Conectar a la base de datos.
	- Cargar el driver de la base de datos.
	- Comprobar el número de valores devueltos (test de COUNTS).
	- Comprobar que la actualización sea realizada de manera correcta

#### Notas CH7 Testable Design
- Evitar métodos **final**, **static** y **private** complejos
- Utilizar **new** con cuidado, implementación no sustituible
- Reducir en la medida de lo posible la lógica de los constructores.
- Tratar de no utilizar el patrón de diseño Singleton tradicional y en su lugar utilizar la aproximación de "crear una sola vez."
- Composición > Herencia.
- Testing de librerías externas.