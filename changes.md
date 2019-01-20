# Changes made to the code

## Document
He puesto el DocumentIdProvider como dependencia por parámetro y que el 
método getDocumentId devuelva un int en vez de un Object.

## DocumentIdProvider
HE creado funciones para poner el environment, nombre de fichero de config y 
driver de DB. Implica tener que poner extra lineas en un orden concreto.
Los dos últimos test los he realizado añadiendo un booleano que simularía
una especie de mocking, que luego modificamos en un doble de la clase.

## Environment
Wrapper para variable de entorno.