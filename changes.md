# Changes made to the code

## [Changed] Class Document.java
- Añadido un Constructor que toma como parámetro directamente el valor del id
  - Al añadirlo este en vez de cambiar el original en producción (main) no es necesario ningún cambio

## [Changed] Class DocumentIdProvider
- Sacar el nombre del driver a una variable por comodidad
- Poner el acceso de `documentId` como `protected` para tener acceso desde la subclase
- getInstance(): Minirefactor por preferencia personal
- DocumentIdProvider(): proceso de obtención del primer `documentId` extraído a la función `createDocumentId()`
- DocumentIdProvider(int documentId): necesario para poder crear un `DocumentIdProvider` con un id inicial fijado
- createDocumentId(): esta a su vez se ha dividido en fragmentos más pequeños, todos ellos en funciones protected para poder ser testeados (AdditionalTest)

## [Added] Class DocumentIdProviderDouble 
- Tiene la funcionalidad básica:
  - Crear un provider a partir del id inicial
  - getDocumentId() que devuelve el actual e incrementa el contador, controlando que el valor sea válido.


# TESTS

## SmokeTest.java
- Implementados los test básicos

## AdditionalTest.java
- Creados los test correspondientes a los ejercicios 5 a. b. c. y d.