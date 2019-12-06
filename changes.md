# Changes made to the code
Document -> getDocumentID ahora devuelve un int
DocuemntIdProvider -> se ha simplificado el constructor a fin de que sirva para todos los casos de prueba.
DocumentIdProvider -> El antiguo contructor ahora se llama "todo" por si se desea mantener su funcionalidad integra
DocumentIdProvider -> Se la modularizado la funcion "todo" (antiguo constructor) para poder testear mejor cada parte
DocumentIdProvider -> Unicamente los modulos imprescindibles se han declarado como protected, para que la clase "DocumentIdProvider" pueda sobreescribirlos.