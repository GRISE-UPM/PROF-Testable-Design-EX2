# Changes made to the code

* 3.a La aplicación genera las plantillas correctamente
    * apply dependency inversion on Document to receive documentId as arguments.
* 3.b La aplicación asigna el número de documento correcto
    * extract config related logics to ConfigProvider 
    * apply dependency inversion on DocumentIdProvider to receive ConfigProvider as arguments
    * add mockito dependency
    * extract MySQL related logics into MySQLHelper.java
    * remove DocumentIdProvider Singleton access
* 3.c Los números de documento asignados a documentos consecutivos son también números consecutivos
    * extract document creation process into DocumentFactory.java
* 5.a La aplicación detecta correctamente que el fichero de configuración no existe.
    * refactor ConfigProvider to receive path as argument
* 5.b La aplicación detecta correctamente que el driver MySQL no existe.
    * wrap reflection API with ReflectionWrapper.java
    * refactor MySQLHelper#getConnection non-static and protected
    * extract protected MySQLHelper#loadDriver
* 5.c La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
    * Extract MySQLHelper complex constructor logic into MySQLHelperFactory