# Changes made to the code

##Document

1) DocumentidProvider es recibido por el constructor y se inicializa ahora this.document.id con este.

##DocumentIdProvider

1) Definimos las variables statement y resultSet de forma protected para que sean accessibles sin problemas

2) En este archivo subdividimos el método DocumentIdProvider en muchos pequeños métodos de manera que sea más sencillo,
   modular y testeable.

3) DocumentIdProvider ahora es protected en vez de private.

4) Uso de booleanos para una de las pruebas en el metodo getLast (ya que de otra manera sino se nos creaban una gran cantidad de líneas nuevas solo para esa prueba).

##HandleDocuments 

1) Se modifica un linea relacionada con la creación de un "new Document" necesario debido a cambios comentados anteriormente

##DocumentIdProviderDouble

1) Hemos sobreescrito los algunos de los métodos relacionados con acciones en base de datos, esto ha sido necesario para la testeabilidad.

##SmokeTest

1) Se han preparado los test que se especifican en el word tanto para el apartado 4 como el 5 de la forma más sencilla posible.