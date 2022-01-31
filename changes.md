# Changes made to the code
- Cambiado el constructor de DocumentIdProvider de privado a protected
- Cambiado el acceso de las variables privadas a protected
- Divido el constructor de DocumentIdProvider en sub-métodos.
	- protected getPath()
	- protected loadProperties(String path)
	- protected loadDBDriver(Properties propertiesInFile)
	- protected connectDB(Properties propertiesInFile)
	- protected readTable(String query)
	- protected getObjetiId(ResultSet resultSet)
		- Difine el documentId a 1622
	- protected closeDBConnections(ResultSet resultSet)
- Divido el método getDocumentId
	- protected updateIdCount(String query)
- Constructor de Document requiere un DocumentIdProvider como parámetro
	- HandleDocumentMainClass crea y pasa como argument un DocumentIdProvider al objeto Document creado
- Creado clase double de DocumentIdProvider
	- Sobreescritos todos los métodos protected para evitar errores
	- Sobreescrito el método getDocumentId

	