# Changes made to the code

## Clase DocumentIdProvider
Para superar las funcionalidades que se piden testear, se han creado metodos publicos
en los que se extrae cada apartado permitiendo testearlo.

## Smoke Test
Han sido necesarios seis test, cuatro de los cuales testean fallos.
- formatTemplateCorrectly: comprueba los apartados 4. a y b. Que el template se forma bien y que el numero del template es el correcto.
- consecutiveDocumentNumber: sacas los Id de dos documentos y comprueba que se diferencian en 1.
- configFileNotExist y configFileNotExist2: comprueba los errores generados al no existir un fichero de configuración en la función "CreateConexion".
- MySQLDriverNotExist: comprueba que se emita el error con un driver erroneo.
- DetectMoreThanOneRowANDDetectIncorrectUpdateId: comprueba la función "CheckUpdateRules" que se ha sustituido en las dos zonas de los test. 