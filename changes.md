# Changes made to the code

## Cambios en en codigo
### DocumentIdProvider
- Eliminación de la lógica en el constructor para una mayor testeabilidad, ya que este era muy complejo.
- Toda esa logica se pone en un metodo nuevo, y se refactoriza en distintos metodos protected, para así poder probar las funcionalidades por separado
- Se han cambiado los atributos private por protected

###Document
- Se cambian los atributos private por protected
- getDocumentId() pasa ahora a ser de tipo Int y no Object


## Adicion de la clase DocumentIdProviderDouble
- clase double que hereda a la otra clase y sobreescribe alguno de los metodos
- Fakea que debuelve un documentId, lo desvinculamos de la conexion con la base de datos.
- creamos un metodo para que cuando se active, de error
