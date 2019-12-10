# Changes made to the code

## DocumentIdProvider
- Hacer públicos los atributos 'ENVIRON', 'documentID' e 'instance'. Esto se hace porque se necesita acceder a estos atributos desde fuera de la clase.
- Hacer público el constructor de la clase para poder llamarlo en los tests.
- Refactor en el constructor. Se ha dividido parte del código en subfunciones para mejorar la lectura y comprensión del código. En el proceso se han creado 2 variables globales 'statement' y 'resultSet' que utilizaran varias de las nuevas funciones.

## Document
- 

## DocumentIdProviderDouble
-