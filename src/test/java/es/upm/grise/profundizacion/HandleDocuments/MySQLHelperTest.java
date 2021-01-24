package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MySQLHelperTest {
    @Mock
    private ConfigProvider configProvider;
    @Mock
    private ReflectionWrapper reflectionWrapper;
    @Mock
    private Connection connection;

    // 5.b La aplicación detecta correctamente que el driver MySQL no existe.
    @Test(expected = NonRecoverableError.class)
    public void testLoadMySQLDriverWhenDriverNotPresent() throws Exception {
        when(reflectionWrapper.findClass("com.mysql.jdbc.Driver")).thenThrow(new ClassNotFoundException());
        new MySQLHelper(configProvider, reflectionWrapper) {
            @Override
            public Connection getConnection(ConfigProvider configProvider) {
                return connection;
            }
        };
    }
}