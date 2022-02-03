# Changes made to the code

CLASE Document

Creado un nuevo contructor en la clase Document
Hemos creado este nuevo constructor para poder inyectar la dependencia DocumentProviderId


CLASE DocumentIdProvider

Cambiar la propiedad documentId a publico: para poder ver su valor

Quitar el tipo static en getInstance(): para poder llamarlo desde otra clase y moquear el metodo

Nuevo contructor para poder probar métodos sin tener que moquear todas las 
dependencia como en el contrutor por defcto 

Cambiado el constructor DocumentIdProvider() a publico para poder usarlo en otras clases como los tests


CLASE TemplateFactory

Quitado el tipo static en el metodo getTemplate para facilitar el testeo