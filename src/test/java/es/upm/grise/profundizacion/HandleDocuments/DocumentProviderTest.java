package es.upm.grise.profundizacion.HandleDocuments;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocumentProviderTest {

	DocumentIdProvider documentIdProvider;

	@Mock
	Connection connection;

	@Mock
	Statement statement;

	@Mock
	ResultSet resultSet;

	@Mock
	PreparedStatement preparedStatement;

	@Rule
	public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

	/**
	 * Los números de documento asignados a documentos consecutivos son también
	 * números consecutivos
	 */
	@Test
	public void consecutiveUpdates_test() throws NonRecoverableError, SQLException {

		when(preparedStatement.executeUpdate()).thenReturn(1);
		when(connection.prepareStatement(any())).thenReturn(preparedStatement);

		documentIdProvider = new DocumentIdProvider(connection);

		int documentId = documentIdProvider.getDocumentId();
		int documentId2 = documentIdProvider.getDocumentId();

		assertEquals(1, documentId);
		assertEquals(2, documentId2);
	}

	/**
	 * La aplicación detecta correctamente que el fichero de configuración no existe
	 */
	@Test(expected = NonRecoverableError.class)
	public void fileNotFound_test() throws NonRecoverableError {
		environmentVariables.set("APP_HOME", "/home");
		documentIdProvider = new DocumentIdProvider();
	}

	/**
	 * La aplicación detecta correctamente que el driver MySQL no existe
	 */
	@Test(expected = NonRecoverableError.class)
	public void driverNotExist_test() throws NonRecoverableError {
		environmentVariables.set("APP_HOME", "");
		documentIdProvider = new DocumentIdProvider();
	}

	/**
	 * La aplicación detecta correctamente que hay más de una fila en la tabla
	 * Counters
	 */
	@Test
	public void moreThanOneRow_test() throws NonRecoverableError, SQLException {
		environmentVariables.set("APP_HOME", "");

		mockStatic(DriverManager.class);

		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getInt(any())).thenReturn(2);
		when(statement.executeQuery("SELECT documentId FROM Counters")).thenReturn(resultSet);
		when(connection.createStatement()).thenReturn(statement);
		when(DriverManager.getConnection(any(), any(), any())).thenReturn(connection);

		documentIdProvider = new DocumentIdProvider();

		assertEquals(2, documentIdProvider.documentId);
	}

	/**
	 * La aplicación detecta correctamente que la actualización del documentID en la
	 * tabla Counters ha sido incorrectamente realizado.
	 */
	@Test(expected = NonRecoverableError.class)
	public void uncorrectUpdate_test() throws NonRecoverableError, SQLException {

		when(connection.prepareStatement(any())).thenThrow(SQLException.class);
		documentIdProvider = new DocumentIdProvider(connection);

		documentIdProvider.getDocumentId();
	}

}