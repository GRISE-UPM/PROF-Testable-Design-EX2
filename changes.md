# Changes made to the code
## Document
- El constructor de la clase se ha dejado vacío para que no se llame a la BD al crearse el objeto y así no tener ninguna dependencia.
- Se ha añadido un nuevo método que permite insertar el identificador del documento.

## DocumentIdProvider
- El constructor de la clase se ha dejado vacío para que no se llame a la BD al crearse el objeto y así no tener ninguna dependencia.
- Se ha añadido un nuevo método que permite crear una conexión a la base de datos.
- Las operaciones que se realizaban en el anterior constructor han sido agrupadas en distintos métodos para poder probarlos de manera independiente y también poder eliminar la dependencia de la BD en el double.

## DocumentIdProviderDouble
- Esta clase es la clase double de DocumentIdProvider y en ella se elimina la dependencia producida por la BD.
