package es.upm.grise.profundizacion.HandleDocuments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    // 5.d La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.
    @Test(expected = NonRecoverableError.class)
    public void testUpdateLastDocumentIdWhenPrepareStatementThrowsException() throws Exception {
        when(connection.prepareStatement("UPDATE Counters SET documentId = ?"))
                .thenThrow(new SQLException());

        mySQLHelper.updateLastDocumentId(1);
    }

    @Test(expected = NonRecoverableError.class)
    public void testUpdateLastDocumentIdWhenSetIntThrowsException() throws Exception {
        final PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement("UPDATE Counters SET documentId = ?"))
                .thenReturn(preparedStatement);
        doThrow(new SQLException()).when(preparedStatement).setInt(1, 1);

        mySQLHelper.updateLastDocumentId(1);
    }

    @Test
    public void testUpdateLastDocumentIdWhenExecuteUpdateThrowsSQLException() throws Exception {
        final PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement("UPDATE Counters SET documentId = ?"))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

        NonRecoverableError exception = null;

        try {
            mySQLHelper.updateLastDocumentId(1);
        } catch (NonRecoverableError e) {
            exception = e;
        }

        assertNotNull(exception);
        verify(preparedStatement).setInt(1, 1);
    }

    @Test(expected = NonRecoverableError.class)
    public void testUpdateLastDocumentIdWhenAffectedRowIsNotOne() throws Exception {
        final PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement("UPDATE Counters SET documentId = ?"))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        mySQLHelper.updateLastDocumentId(1);
    }
}