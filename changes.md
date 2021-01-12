# Changes made to the code
## Document
* Eliminar todos los privados de las variables
* Agregar DocumentIdProvider como un parámetro de constructor

## TemplateFactory
En esta clase no hace falta hacer ninguna modificación para poder hacerla testable. 
Sería recomendable cambiar el método estático en un método de clase.

## DocumentIdProvider
* Quitar el private del constructor, esto ayuda a crear una clase que heredé de esta y evitar toda la 
    lógica del constructor.

* Dividir la lógica del constructor para poder realizar de los distintos bloques que lo componen.

## HandleDocumentsMainClass
Al modificar Document hemos tenido que modificar todas las clases que dependen de el