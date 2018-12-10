# Changes made to the code

[Document.java]
- Se ha cambiado la asignacion del Id con un metodo especifico

[DocumentIdProvider.java]
- Se ha eliminado el atributo "private" del instance del DocIdProvider
- getInstance() del Provider recibe el driver como parámetro

[SmokeTest.java]

 TESTS OK
 	- plantillaCorrecta() -> comprubea que funciona correctamente cuando plantilla correcta
 	- driverCorrecto() -> comprubea que funciona correctamente cuando driver correcto
 	- documentCorrecto() -> comprubea que funciona correctamente cuando document correcto
 
 TESTS ERRORES
 	- testErrorProperty() -> comprubea que funciona correctamente cuando properties erroneas
 	- testErrorDriver() -> comprubea que funciona correctamente cuando driver erroneo
 	- testErrorDocument() -> comprubea que funciona correctamente cuando document erroneo