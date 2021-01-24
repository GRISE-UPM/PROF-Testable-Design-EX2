package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MySQLHelperTest {
    @Mock
    private Connection connection;

    private MySQLHelper mySQLHelper;

    @Before
    public void setup() {
        mySQLHelper = new MySQLHelper(connection);
    }

    // 5.c La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
    @Test(expected = NonRecoverableError.class)
    public void testGetDocumentIdWhenMoreThanOneRowReturned() throws Exception {
        final Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        final ResultSet resultSet = mock(ResultSet.class);
        when(statement.executeQuery("SELECT documentId FROM Counters")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("documentId")).thenReturn(0).thenReturn(1);

        mySQLHelper.getLastDocumentId();
    }
}