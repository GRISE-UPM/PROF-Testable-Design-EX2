package es.upm.grise.profundizacion2018.tema5;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javafx.beans.binding.When;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DocumentTest {


	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void check_format_template_is_correct() throws NonRecoverableError, RecoverableError {
		DocumentIdProvider mockDocumentIdProvider = mock(DocumentIdProvider.class);
		int counter = 5;
		when(mockDocumentIdProvider.getDocumentId()).thenReturn(counter);
		Document d = new Document(mockDocumentIdProvider, "DECLARATION", "B", "A", "C");
		assertEquals("DOCUMENT ID: 5\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}

	@Test
	public void check_number_in_template() throws NonRecoverableError, RecoverableError, IOException, SQLException {
		//ARRANGE
		EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
		when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

		ResultSet mockResult = mock(ResultSet.class);
		when(mockResult.next()).thenAnswer(new Answer<Boolean>() {

			boolean firstTime = true;
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				if(firstTime) {
					firstTime = false;
					return true;
				}
				return firstTime;
			}
		});
		when(mockResult.getInt(Matchers.anyString())).thenReturn(10);

		DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
		when(mockDatabaseProvider.executeQuery(Matchers.anyString())).thenReturn(mockResult);
		when(mockDatabaseProvider.executeUpdateQuery(Matchers.anyString(), Matchers.anyInt())).thenReturn(1);
		ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

		Properties mockProperties = new Properties();
		mockProperties.put("url", "url");
		mockProperties.put("username", "username");
		mockProperties.put("password", "password");
		when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

		//ACT
		DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);

		Document d1 = new Document(documentIdProvider, "DECLARATION", "B", "A", "C");
		assertEquals(11, d1.getDocumentId());
	}

	@Test
	public void check_consecutive_numbers_in_template() throws NonRecoverableError, RecoverableError, IOException, SQLException {
		//ARRANGE
		EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
		when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

		ResultSet mockResult = mock(ResultSet.class);
		when(mockResult.next()).thenAnswer(new Answer<Boolean>() {

			boolean firstTime = true;
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				if(firstTime) {
					firstTime = false;
					return true;
				}
				return firstTime;
			}
		});
		when(mockResult.getInt(Matchers.anyString())).thenReturn(10);

		DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
		when(mockDatabaseProvider.executeQuery(Matchers.anyString())).thenReturn(mockResult);
		when(mockDatabaseProvider.executeUpdateQuery(Matchers.anyString(), Matchers.anyInt())).thenReturn(1);
		ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

		Properties mockProperties = new Properties();
		mockProperties.put("url", "url");
		mockProperties.put("username", "username");
		mockProperties.put("password", "password");
		when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

		//ACT
		DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);

		Document d1 = new Document(documentIdProvider, "DECLARATION", "B", "A", "C");
		Document d2 = new Document(documentIdProvider, "DECLARATION", "B", "A", "C");
		assertEquals(11, d1.getDocumentId());
		assertEquals(12, d2.getDocumentId());
	}

	@Test
	public void check_incomplete_document_exception() throws NonRecoverableError, RecoverableError, IOException, SQLException {
		//ARRANGE
		EnvironmentHandler mockEnvironmentHandler = mock(EnvironmentHandler.class);
		when(mockEnvironmentHandler.getVariable("APP_HOME")).thenReturn(new File(".").getCanonicalPath());

		ResultSet mockResult = mock(ResultSet.class);
		when(mockResult.next()).thenAnswer(new Answer<Boolean>() {

			boolean firstTime = true;
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				if(firstTime) {
					firstTime = false;
					return true;
				}
				return firstTime;
			}
		});
		when(mockResult.getInt(Matchers.anyString())).thenReturn(10);

		DatabaseProvider mockDatabaseProvider = mock(DatabaseProvider.class);
		when(mockDatabaseProvider.executeQuery(Matchers.anyString())).thenReturn(mockResult);
		when(mockDatabaseProvider.executeUpdateQuery(Matchers.anyString(), Matchers.anyInt())).thenReturn(1);
		ConfigurationHandler mockConfigurationHandler = mock(ConfigurationHandler.class);

		Properties mockProperties = new Properties();
		mockProperties.put("url", "url");
		mockProperties.put("username", "username");
		mockProperties.put("password", "password");
		when(mockConfigurationHandler.readProperties(Matchers.any())).thenReturn(mockProperties);

		//ASSERT
		thrown.expect(RecoverableError.class);
		thrown.expectMessage(Error.INCOMPLETE_DOCUMENT.getMessage());

		//ACT
		DocumentIdProvider documentIdProvider = new DocumentIdProvider(mockEnvironmentHandler, mockConfigurationHandler, mockDatabaseProvider);
		Document d = new Document(documentIdProvider, null, "A", "T", "B");
		d.getFormattedDocument();

	}


}
