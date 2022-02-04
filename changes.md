# Changes made to the code

- Modificamos el contructor para pasarle el id por param.
- getDocumentId(int lastId) id por param.
- Crear un get nuevo para la variable de DocumentId.
- Cambiar a public DocumentIdProvider().
- nonRecoverableError añadir type para poner el tipo de error.
- public DocumentIdProvider(String path) meter path por param.
- Cambiar DocumentIdProvider(String path, String DBDriver, String urlParam, String userParam, String passwordParam) para poder testear.