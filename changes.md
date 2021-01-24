# Changes made to the code

* La aplicación genera las plantillas correctamente
    * apply dependency inversion on Document to receive documentId as arguments.
* La aplicación asigna el número de documento correcto
    * extract config related logics to ConfigProvider 
    * apply dependency inversion on DocumentIdProvider to receive ConfigProvider as arguments
    * add mockito dependency
    * extract MySQL related logics into MySQLHelper.java
    * remove DocumentIdProvider Singleton access