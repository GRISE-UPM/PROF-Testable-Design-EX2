# PROF-Testable-Design
Testable design exercise
**Document
-Las variables pasan a ser protected y no private

**DocumentIdProvider
-Cambio de private a protected en algunas variables.
-Los metodos pasan a ser public y no privados
-Cambio de la estructura del constructor simplificandola de tal forma que solo realizará una llamada a un método
-Creación de métodos individuales que realizan lo que anteriormente realizaba el costructor, todos ellos serán protected

**DocumentIdProviderDouble
-Clase que "hija"" de DocumentIdProvider
-Sobreescribirá ciertos metodos de la clase padre para poder realizar las pruebas correspondientes sin modificar la clase padre