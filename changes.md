# Changes made to the code

## He realizado los siguientes cambios

- La clase DocumentIdProvider ha pasado de ser Singleton y todos los metodos static a ser una clase instanciable y 
 ahora se pasa como referencia al contruir el objeto Document.
- El constructor de DocumentIdProvider si esta en modo "test" no realiza la conexion con la BBDD pero en cambio si esta 
en produccion si 
- La variable de entorno PATH lo he puesto como posible parametro del contructor de la clase DocumentIdProvider, si es 
 null la variable de entorno y si tiene un string ese es el valor que se le asigna a la variable path. 
- El metodo getDocumentId de DocumentIdProvider he separado la parte de actualizar el valor en BBDD para que no la 
realize al hacer el test y si en fase de produccion