# Changes made to the code

## Document

DocumentidProvider se recibe por el constructor.
Las variables author, body, template, title y documentId son ahora protected.

## DocumentIdProvider

Las variables ENVIRON, documentId e instance son ahora protected
División del método DocumentIdProvider en mpara que sea testeable y protected.

## DocumentIdProviderDouble

Se duplica la clase DocumentIdProvider para realizar los tests.

## HandleDocuments

Se cambia la creación de un deun documento nuevo.

## SmokeTest

Creación de los test.
