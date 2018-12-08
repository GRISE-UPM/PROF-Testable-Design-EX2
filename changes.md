# Changes made to the code
### 1. DocumentIdProvider class
Se ha creado un nuevo método connectDB(), que se encarga de la conexión con la base de datos y se ha eliminado el
constructor de la clase. Para la obtención de una instacia de la clase se ha "mantenido" el código original con la difencia
de que se realiza una conexión a la base de datos con el método connectDB(). Además, getInstance() a pasado de ser estático
a no serlo con el objetivo de poder realizar @Override a la hora de realizar los test y adecuar nuestra clase hija
DocumentIdProviderDouble a nuestro antojo. Por último, se han añadido varios atributos a la clase con el objetivo de 
realizar de forma más cómoda los test ConnectionTest, DBTest y FileTest. 
### 2. Document class
Se ha implementado un método loadDocumentIdProvider(DocumentIdProvider document) que se encarga de realizar un set del atributo 
*DocumentIdProvider documentIdProvider*  que tiene la clase Document y así poder modificarlo fácilmente en los tests.

By Celia Caro Gómez