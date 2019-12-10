# Changes made to the code

## Document:

- Las variables `documentId`, `template`, `author`, `title` y `body` se han modificado para que en vez de private sean `protected`.
- El `documentIdProvider` ahora se inyecta por constructor.



## HandleDocumentsMainClass:

- Se modifica la creación del documento.



## DocumentIdProvider:

- Las variables `ENVIRON`, `documentId` e `instance` se han modificado para que en vez de private sean `protected`.
- El método `DocumentIdProvider` se ha modificado para que en vez de private sea `protected`.
- Se separa el constructor en varios métodos para que su seguimiento sea más simple.



## DocumentIdProviderDouble:

- Herencia de `DocumentIdProvider` para la realización de los tests.



## SmokeTest:

- Se crean los test para el apartado `4`.
- Se crean los test para el apartado `5`.