# Changes made to the code

1. Se ha extraido la interfaz de DocumentIdProvider para permitir la injectcion de dependencias en el constructor de la clase Documento.

2. Se cambia el método getTemplate a no estático y se vuelve a hacer una extracción de la interfaz y se hace lo mismo que en el paso 1.

3. Para que funcionen los test, se hace una nueva clase fake que actue como DocumentIdProvider y devuelva un valor para poder comparar los test 