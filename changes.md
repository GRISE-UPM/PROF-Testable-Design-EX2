# Changes made to the code

1. Constructor creado en clase Document que permite asignar el id deseado.
2. En el método getDocumentId() se añade el parámetro id para un manejo más fácil de las pruebas.
3. DocumentIdProvider pasa a ser protected para poder ser invocado.
4. Al método DocumentIdProvider se le añade la ruta del fichero para facilitar las pruebas.
5. Además, se añade también el driver de la base de datos como parámtero del método.
6. Se modifica la clse NonRecoveableError para poder devolver el tipo de exception según se necesite.