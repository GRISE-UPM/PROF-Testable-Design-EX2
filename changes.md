# Changes made to the code

## Document:
- Se cambia la visibilidad de las variables `template`, `author`, `title` y `body` a `protected`, en vez de private.
- Se añade una instancia `DocumentIdProvider` como parámetro en el constructor.
- Se añade un atributo de clase `TemplateFactory` y como parámetro en el constructor, ya que el método `getTemplate` no puede ser estático.
- Se cambia el valor de retorno del método `getDocumentId` por `int`.

## TemplateFactory:
- Se añade un constructor de tipo `Singleton` para evitar llamadas estáticas al método `getTemplate`.

## DocumentIdProvider
- Se cambia la visibilidad de las variables `ENVIRON`, `documentId` e `instance` a `protected`, en vez de private.
- Se cambia la visibilidad del método `getDocumentId` a `protected`, en vez de public.
- Se simplifica el constructor creando los métodos `getPath`, `getProperties`, `loadDBDriver`, `createDBConnection` y `getID`, para que sea más sencillo su comprensión y seguimiento.
- La visibilidad de estos métodos anteriores y el constructor `DocumentIdProvider` se cambia a `protected`, para realizar las pruebas necesarias.

## HandleDocumentsMainClass
- Se ajusta al nuevo constructor de la clase `Document`.

## DocumentIdProviderDouble
- Se crea esta clase para realizar los test correspondientes que involucran a `DocumentIdProvider`

##### Observaciones
- Se ha tenido que comentar las llamadas de métodos del constructor de la clase `DocumentIdProvider` para que no se intente conectar a la base de datos cuando se crea una nueva instancia de la clase `DocumentIdProviderDouble`.