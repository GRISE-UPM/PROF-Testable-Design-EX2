# Changes made to the code
## Document.java
Cambiado el método de asignación del documentId, en vez de hard-codeado en el constructor, se realiza
mediante una función setDocumentId que toma como parámetro el DocumentIdProvider del que cogerlo.

## DocumentIdProvider
La clase ha sido modificada para tener más facilidad de generación de un doble.
- El código de cargar el fichero de propiedades ha sido refactorizado a una función.
- El código de conexión a la DB ha sido refactorizado a una función.
- El código para la búsqueda del último ID ha sido refactorizado a una función.
- EL código para encontrar el document ID ha sido refactorizado a una función.

## RecoverableError / NonRecoverableError
Añadidos los constructores con mensaje por parámetro.

## HandleDocuments
Carga el DocumentIdProvider del Document con el MYSQL Driver.