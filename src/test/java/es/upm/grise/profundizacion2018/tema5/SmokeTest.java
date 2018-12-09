package es.upm.grise.profundizacion2018.tema5;

import static es.upm.grise.profundizacion2018.tema5.Error.CANNOT_RUN_QUERY;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

public class SmokeTest {
	
	class DocumentIdProviderDouble extends DocumentIdProvider{
		public DocumentIdProviderDouble() throws NonRecoverableError{			
		}		
	}
	@Test
	public void formatTemplateCorrectlyAndCorrectId() throws NonRecoverableError, RecoverableError {		
		Document d = new Document();
		int documentId = d.getDocumentId();
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		assertEquals("DOCUMENT ID: " + documentId + "\n\nTitle : A\nAuthor: B\n\nC", d.getFormattedDocument());
	}
	
	@Test 
	public void consecutiveCallsShouldReturnConsecutiveNumbers() throws NonRecoverableError, RecoverableError {		
		Document firstDocument = new Document();
		Document secondDocument = new Document();
		int firstDocumentId = firstDocument.getDocumentId();
		int secondDocumentId = secondDocument.getDocumentId();
		assertEquals(firstDocumentId + 1, secondDocumentId);
	}
	
	@Test(expected = NonRecoverableError.class)
	public void ifConfigFileDoesntExitsShouldThrowException() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;
		documentIdProvider.checkIfConfigFileExists(propertiesInFile, inputFile, "falsePath");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void ShouldThrowExceptionIfDriverDoesntExists() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		documentIdProvider.loadBdDriver("nonExisting.Driver");
	}
	
	@Test(expected = NonRecoverableError.class)
	public void ShouldThrowExceptionIfDifferentThanOneCounters() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		Properties propertiesInFile = new Properties();
		InputStream inputFile = null;
		documentIdProvider.checkIfConfigFileExists(propertiesInFile, inputFile, System.getenv(documentIdProvider.ENVIRON));
		String url = propertiesInFile.getProperty("url");
		String username = propertiesInFile.getProperty("username");
		String password = propertiesInFile.getProperty("password");
		documentIdProvider.loadBdDriver("com.mysql.jdbc.Driver");
		documentIdProvider.connection = documentIdProvider.createDbConnection(url, username, password);
		
		String query = "SELECT documentId FROM Counters";
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			statement = documentIdProvider.connection.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {

			System.out.println(CANNOT_RUN_QUERY.getMessage());          	
			throw new NonRecoverableError();

		}
		documentIdProvider.numberOfValues = 2;
		documentIdProvider.checkNumOfValues();
	}
	
	@Test
	public void documentIdCorrectlyChangedInCounter() throws NonRecoverableError{
		DocumentIdProviderDouble documentIdProvider = new DocumentIdProviderDouble();
		Document d = new Document();
		assertEquals(documentIdProvider.getDocumentId(), d.getDocumentId());
	}
}
