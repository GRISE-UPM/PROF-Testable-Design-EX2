package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.Test;

public class ConfigProviderTest {
    // 5.a La aplicación detecta correctamente que el fichero de configuración no existe.
    @Test(expected = NonRecoverableError.class)
    public void testWhenConfigNotExist() throws Exception {
        new ConfigProvider("no-exist");
    }
}