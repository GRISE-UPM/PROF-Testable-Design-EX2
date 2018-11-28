# Changes made to the code

- Se han transformado `documentId`, `template`, `author` y `title` (`Document.java`) de `private` a `package`: Es necesaria una forma de acceder a estos datos y `package` nos permite duplicar la clase para probarla.
- Se han transformado `ENVIRON`, `documentId` y `instance` (`DocumentIdProvider.java`) de `private` a `package` por el mismo motivo.
- Se ha dividido el constructor de `DocumentIdProvider.java` para poder ejecutar pruebas de forma separada en sus distintos componentes.
- Se han añadido los constructores de `NonRecoverableError.java` (utilizando los de su clase padre) para que los nuevos errores puedan tener mensaje y causa, que podrán ser evaluados en los casos de prueba.
