# Changes made to the code

Se han cambiado el acceso private de algunas variable a protected con el fin de poder usasrlas correctamente en otras clases para hacer el testing.

Se ha transformado el metodo constructor de idprovider con la intención de dividirlo en varios trozos para así facilitar el testing. Además el constructor pasa a ser público para que todos los demás puedan verlo. Estos métodos generados serán protected.

He creado una nueva clase que hereda de DocumentIdProvider para hacer las pruebas, consiguiendo así que no se trabaje drectamente sobre la base de datos, por si se diese el caso de que hubiese algun error y pudiese destrozarla. Además para conseguir el error de las filas en getlastObjectId cambie el método para que si le paso una variable booleana false lance ese error.Para getDocumentId también se ha cambiado el método para obtener el fallo de actualización del contador cuando llegue el id a un límite de 4.

Todos los tests que se pedían han sido implementados.


