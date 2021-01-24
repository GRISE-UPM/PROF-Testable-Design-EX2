package es.upm.grise.profundizacion.HandleDocuments;

public class ReflectionWrapper {
    public Class<?> findClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}
