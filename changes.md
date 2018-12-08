# Changes made to the code


## Clase `Document`
* Se ha modificado el metodo _`getDocumentId`_ para retornar un entero
* Se ha añadido un nuevo constructor que acepta como parametro un _`DocumentIdProvider`_. Con esto conseguimos pasar desde nuestro test el customizado.

## Clase `DocumentIdProvider`
* Se ha hecho una refactorización del codigo extrayendo en pequeñas funciones la lógica principal. No afecta a la funcionalidad.
* Se ha extraido en una una función _`getPath`_ la obtención del path
* Se ha extraido en una propiedad el nombre del driver y se ha añadido un getter para obtenerlo
* Mismo comportamiento para _`numberOfValues`_ y _`numUpdatedRows`_

## Clase `SmokeTest`
* Se han implementado los métodos necesarios para comprobar todas las especificaciones
* Se ha añadido la clase _`DocumentIdProviderDouble`_. En esta clase se han efectuado los siguientes cambios:
    * Se trata de un clase que extiende de _`DocumentIdProvider`_
    * En el constructor se pasan los parametros:
    ```
    String path,
    String jdbc_driver,
    boolean moreThanOneDocument,
    boolean moreThanOneDocumentUpdated
    ```
    * Se han sobreescrito los getters mencionados en la clase _`DocumentIdProvider`_. En base a los parametros pasados por constructor se llamará al metodo de la clase padre o se obtendrá el valor de la variable de la clase double.