# Changes made to the code

Estos son las modificaciones que he realizado en el código para que sea testeable.

- Cambio el documentid a PROTECTED
- Hacer PUBLIC el documentId en la clase DocumentIdProvider
- Quitar static del método: public DocumentIdProvider getInstance() throws NonRecoverableError
- He creado un nuevo contructor en la clase Document para inyectar la dependencia DocumentProviderId
- Método documentIdProvider cambiamos la visibilidad a public
- Modificar el método DocumentIdProvider para pasarle como parametros de entrada la infomacion de conexion a la DB. 
- Modificamos el metodo getInstance() de la clase DocumentIDProvider para establecer la conexion con la DB pasandole los parametros correctos en el constructor. 
- En SmokeTest he implementado test para comprobar el correcto funcionamiento del código 
