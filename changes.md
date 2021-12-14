# Changes made to the code

ENVIRON protected (no se si es necesario)

DocumentIDProvider no privada

Simplificar el constructor para poder doblar

DocumentId en document a protected

La funcion que quedaba con el antiguo codigo del constructor se ha
reducido a otras funciones auxiliares para poder testarlas
con idependencia

Se ha doblado resultSet para otras pruebas, con mockito no seria necesario
