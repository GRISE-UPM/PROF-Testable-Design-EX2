# Changes made to the code

# Document

## En el constructor se inserta un documentIdProvider en vez de crearlo

## Tambien se inserta un templateFactory ya que no se pueden realizar llamadas estaticas. 

## El metodo getDocumentId devuelve int en vez de Object.

# DocumentIdProvider

## Se declara el constructor como protected 

## Se ponen a protected los metodos para que se puedan cambiar por el testdouble.

## Se simplifica el constructor creando los metodos: propLoader, driverLoader, connCreator y getID

# TemplateFactory 

## El constructor ya no es estático. Se cambia a singleton.

# HandleDocumentMainclass

## Se ajusta la creación de un Document al nuevo constructor