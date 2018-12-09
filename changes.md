# Changes made to the code
## DocumentIdProvider.java

- Se ha repartido la funcionalidad de la clase en diferentes métodos más pequeños con menor responsabilidad para poder doblar la case en los tests y sobrecargar sólo los métodos necesarios.
- Se han cambiado algunas variables locales por atributos para facilitar la tarea del punto anterior
- Se añade un mensaje a cada lanzamiento de excepción para que sea más fácilmente testable la causa de la excepción que con la impresión por consola

## Document.java

- Se ha añadido el método público loadProvider(DocumentIdProvider provider) para poder aplicar la inversión de dependencias y utilizar los dobles de DocumentIdProvider que se crean en los tests
- Se añade un mensaje a cada lanzamiento de excepción para que sea más fácilmente testable la causa de la excepción que con la impresión por consola

## NonRecoverableError.java y RecoverableError.java

- Se han modificado estas excepciones para que puedan recibir un string que se asignará al mensaje de las mismas

# Consideraciones

- Si se ejecutan a la vez los tests de las clases DocumentTest.java y DocumentIdProviderTestD.java, DocumentTest falla, pero si se ejecutan por separado el funcionamiento es correcto en todos los tests. Se han invertido bastantes horas en buscar la raíz del problema y no he sido capaz de encontrar dónde está el error.