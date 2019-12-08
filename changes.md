# Changes made to the code
- Clase "Document" -> getDocumentID ahora devuelve un int.
- Clase "DocuemntIdProvider" -> se ha simplificado el constructor a fin de que sirva para todos los casos de prueba.
- Clase "DocumentIdProvider" -> El antiguo contructor ahora se llama "todo" por si se desea mantener su funcionalidad integra.
- Clase "DocumentIdProvider" -> Se ha modularizado la función "todo" (antiguo constructor) para poder testear mejor cada parte.
- Clase "DocumentIdProvider" -> únicamente los módulos imprescindibles se han declarado como protected, para que la nueva clase "DocumentIdProviderDouble" pueda sobreescribirlos.
- Clase "DocumentIdProviderDouble" -> Se ha creado la clase "DocumentIdProviderDouble" para poder realizar más pruebas de manera específica
