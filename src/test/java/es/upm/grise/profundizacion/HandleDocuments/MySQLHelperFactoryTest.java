package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MySQLHelperFactoryTest {
    @Mock
    private ReflectionWrapper reflectionWrapper;

    private MySQLHelperFactory mySQLHelperFactory;

    @Before
    public void setup() {
        mySQLHelperFactory = new MySQLHelperFactory();
    }

    // 5.b La aplicación detecta correctamente que el driver MySQL no existe.
    @Test(expected = NonRecoverableError.class)
    public void testLoadMySQLDriverWhenDriverNotPresent() throws Exception {
        when(reflectionWrapper.findClass("com.mysql.jdbc.Driver")).thenThrow(new ClassNotFoundException());
        mySQLHelperFactory.loadMySQLDriver(reflectionWrapper);
    }
}