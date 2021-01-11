# Changes made to the code
Document.java -> DocumentIdProvider se pasa por constructor
DocumentIdProvider.java -> Creados metodos auxiliares protected para separar funcionalidad (hay que evitar lógica compleja en el constructor). Se ha modificado el método para recoger el config.properties para que no necesite de variable de sistema. Ahora si no conecta con la base de datos se utiliza ids locales, comenzando por 0.
HandleDocumentsMainClass -> Se ha modificado para que funcione correctamente con los cambios descritos anteriormente
------
SmokeTest.java -> Se han incluido los métodos restantes y se ha comprobado que todos dan positivo
DocumentIdProviderMock -> Clase mock (o double) que ayuda a poder realizar los tests correctamente sin afectar a la funcionalidad del software