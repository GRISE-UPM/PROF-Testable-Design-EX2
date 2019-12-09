# Changes made to the code

##En la clase Document.class
Transforme todos los variables de de private a protected para poder acceder a ellas en todo el proyecto.
Además en el método getDocumentId tranforme su tipo Object a int ya que la variable documentId es un int.

##En la clase DocumentIdProvider.class 
Transforme las variables "ENVIRON","documentId", "connection", "instance", "propertiesInFile" y "inputFile" de private a proteted para poder acceder a ellas en todo el proyecto.
Extraí del constructor las variables "statement","resultSet", "propertiesInFile" y "inputFile" e inicializando todas las variables.
En el constructor extraí toda su implementación y la trocee en los siguientes métodos: 
getPath():Obtengo el path con la variable ENVIRON.
getProperties(Properties properties, InputStream inputFile, String path): Obtengo la información de la bbdd gracias a el path y el archivo config.properties.
loadDrivers(String driver): Cargo los drivers.
createConnection(String url, String username, String password):Creo la conexión sql con la información de la url, username y password obtenida gracias al método getProperties.
executeQuery(Connection connection):Ejecuta la sentencia sql "SELECT documentId FROM Counters".
connnect(): Implementé el método connect para simular toda la línea de ejecución que tenía el constructor compactamente. Este método llama a los métodos getPath(),getProperties(propertiesInFile,inputFile,path),loadDrivers(driver),createConnection(url, username, password);
getID(boolean conection):Obtengo el id del documento llamando a connect() y acto seguido al método executeQuery(connection).

##En la clase DocumentIDInstances
Esta clase es una clase implementada desde cero y que extiende a la clase DocumentIdProvider. 
Toda esta clase se encarga de mockear la bbdd para no realizar el testing sobre ella. 
Para ello sobreescribí todos los métodos creados en la clase DocumentIdProvider, cambiando el resultado en getID() para que devuelva un 1 y getDocumentId para conseguir la excepcion CANNOT_UPDATE_COUNTER.
En el resto de métodos simplemente llamo a la clase super con el método correspondiente.

##En la clase smokeTest
Implmenté los 8 test:
templateFromatOk(): Comprueba que el formato del template es correcto.
numeroOk():Comprueba que el númeto del documento sea el correcto.
numerosAsignadosOk(): Comprueba que se asignan consecutivamente los id.
testNoExisteDriver(): Comprueba los drives se cargan mal.
testNoFicheroConfiguracion(): Compruebo que las variables propertiesInFile y inputFile se cargan mal.
testMasDeUnaFila(): Compruebo que no se pueda establecer más de una fila.
testNoActualizacion(): Compruebo que no se pueda actualizar el id despues de 5 ocasiones.



