# Changes made to the code
- Eliminada la lógica del constructor de DocumentIdProvider al método initDocumentIdProvider
- Cambiado el acceso de las variables privadas a protected
- Divido initDocumentIdProvider en sub-métodos.
	- protected getPath()
	- protected loadProperties(String path)
	- protected loadDBDriver(Properties propertiesInFile)
	- protected connectDB(Properties propertiesInFile)
	- protected readTable(String query)
	- protected getObjetiId(ResultSet resultSet)
	- protected closeDBConnections(ResultSet resultSet)
- Divido el método getDocumentId
	- protected updateIdCount(String query)
- Constructor de Document requiere un id como parámetro
	- HandleDocumentMainClass crea el Documento y le pasa como argument un id proporcionando por el DocumentIdProvider inicializado
- Creada clase double de DocumentIdProvider
	- Añadido método setStartingId: Define el ID inicial para los tests
	- Sobreescrito el método getDocumentId: aumenta en 1 el DocumentId actual y lo devuelve
- Creado constructor para SmokeTest: Inicializa un objeto DocumentIdProviderDouble
- Creados 6 tests en SmokeTest
	- idAssignationCorrect
	- formatTemplateCorrectly
	- consecutiveDocumentIDTest
	- noConfigurationFileTest
	- noDriverToLoadTest
	- ResultSetErrorTest (se ha usado técnica de mocking)

	