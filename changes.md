# Changes made to the code
[ Document.java ]

Se borra el constructor y se deja el constructor vacio para poder extender luego y crear una clase DocumentDouble para pruebas.
Se crea el metodo setDocumentId para que se pueda escribir el ID del documento.

[ HandleDocuments ]

Se agrega la sentencia: "document.setDocumentId(DocumentIdProvider.getInstance().getDocumentId())" para el objeto Documento tenga el id que le corresponde.

[ DocumentIdProvider ]

Se hacen variables de clase a "statement", "propertiesInFile", "resultSet", y "path". Las 2 ultimas ademas se establecen como protected.
Se cambia el constructor a un constructor vacio. El antiguo constructor pasa a ser el metodo -> CreateConnectionDatabase() que se encarga de establecer la conexion a la base de datos.
Ademas, dentro del nuevo metodo "CreateConnectionDatabase" se sacan fueran varias sentencias creando diferentes estos nuevos metodos:
  - GetPropertyFile -> Nos permitira probar luego si se ha producido un error al abrir el fichero de propiedades
  - LoadBBDD_Driver -> Nos permitira probar si hay un error a la hora de cargar el driver de la BBDD
  - ReadCountersTable
  - GetLastObjectID -> Nos permitira luego probar si hay mas de una fila en "Counters"

Por ultimo en getDocumentId se saca la logica de actualizacion del documentID a otro metodo -> DocumentIDUpdate(). Esto nos permitira luego probar el comportamiento cuando se actualiza mas de una fila al actualizar el documentID.

[ DocumentDouble ]

Clase que extiende de Document. En ella, establece el documentID llamando a la clase DocumentIdProviderDouble.

[ DocumentIdProviderDouble ]

Clase que extiende de DocumentIdProvider. Emula la conexion a la BBDD y se usa para las 3 primeras pruebas

[ DocumentIdProviderDouble2 ]

Clase que extiende de DocumentIdProvider. 

Sobreescribe dos metodos para poder hacer pruebas:
- GetLastObjectID
- DocumentIDUpdate

Se usa en las 4 ultimas pruebas.