# Changes made to the code

## DocumentIdProvider
- Refactor en el constructor. Se ha dividido parte del código en subfunciones para mejorar la lectura y comprensión del código.
- Añadir variables constantes "Connection", "Statement" y "ResultSet" para el uso de la DB.
- Añadir variable constante "Driver" para el driver.

## Document
- Añadir el id del documento por parametro en el constructor.

## HandleDocumentsMainClass
- Añadir en el constructor el ID del documento de la instancia del DocumentIdProvider.

## DocumentIdProviderDouble
- Creado desde 0.
- Creado para poder testear el DocumentIdProvider.

## DocumentIdProviderDoubleErrorRows
- Creado desde 0.
- Creado para poder testear el DocumentIdProvider.
